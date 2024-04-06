package com.vainaweb.schoolsystem.integration.student;

import java.util.HashMap;
import java.util.Map;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@DirtiesContext
public class StudentUpdateTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private Flyway flyway;

  private Map<String, Object> body = new HashMap<>();

  @BeforeEach
  public void setup() {
    jdbcTemplate.execute("DROP ALL OBJECTS DELETE FILES");
    flyway.migrate();
  }

  @Test
  @DisplayName("Update Student Not Found")
  public void updateStudentNotFound() throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.put("/estudantes/4")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @DisplayName("Update Student Non Numeric Id")
  public void updateStudentNonNumericId() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.put("/estudantes/a")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("The parameter 'id' is missing.")));
  }

  @Test
  @DisplayName("Name Update Student Success")
  public void nameUpdateStudent() throws Exception {

    body.put("name", "João da Silva");

    mockMvc.perform(MockMvcRequestBuilders.put("/estudantes/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("If-Match", "adc41273"))
        .andExpect(MockMvcResultMatchers.status().isNoContent())
        .andExpect(MockMvcResultMatchers.header().string("ETag", Matchers.containsString("d617296b")));
  }

  @Test
  @DisplayName("Name Update Student If-Match non match")
  public void nameUpdateStudentNonMatch() throws Exception {

    body.put("name", "João da Silva");

    mockMvc.perform(MockMvcRequestBuilders.put("/estudantes/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("If-Match", "a"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("the received ETag does not match the current ETag"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Name Update Student is blank")
  public void nameUpdateStudentIsBlank() throws Exception {

    body.put("name", "");

    mockMvc.perform(MockMvcRequestBuilders.put("/estudantes/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("If-Match", "adc41273"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.name").value("must not be blank"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("update Student with course is invalid")
  public void updateStudentWithCourseBlank() throws Exception {
    body.put("course", "");
    mockMvc.perform(MockMvcRequestBuilders.put("/estudantes/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("the provided course is not valid."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("update Student with phone invalid format")
  public void updateStudentWithPhoneInvalidFormat() throws Exception {

    body.put("phone", "teste");

    mockMvc.perform(MockMvcRequestBuilders.put("/estudantes/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("If-Match", "adc41273"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.phone")
            .value("must match (##) 9####-#### or (##) ####-####"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("update Student with zip invalid format")
  public void updateStudentWithZipInvalidFormat() throws Exception {

    Map<String, Object> address = new HashMap<>();
    address.put("zip", "teste");
    body.put("address", address);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/estudantes/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.zip").value("must match #####-###"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Update Student with street invalid format")
  public void updateStudentWithStreetInvalidFormat() throws Exception {

    Map<String, Object> address = new HashMap<>();
    address.put("street", "");
    body.put("address", address);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/estudantes/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.street").value("must not be blank"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Update Student with number invalid")
  public void updateStudentWithNumberInvalid() throws Exception {

    Map<String, Object> address = new HashMap<>();
    address.put("number", -1);
    body.put("address", address);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/estudantes/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.number").value("must be greater than or equal to 0"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Update Student with number is non number")
  public void updateStudentWithNumberNonNumber() throws Exception {

    Map<String, Object> address = new HashMap<>();
    address.put("number", "a");
    body.put("address", address);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/estudantes/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .value("the HTTP request contains malformed syntax or cannot be understood by the server."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Update Student with city is blank")
  public void updateStudentWithCityBlank() throws Exception {

    Map<String, Object> address = new HashMap<>();
    address.put("city", "");
    body.put("address", address);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/estudantes/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.city").value("must not be blank"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("update Student with state city is invalid")
  public void updateStudentWithStateInvalid() throws Exception {

    Map<String, Object> address = new HashMap<>();
    address.put("state", "teste");
    body.put("address", address);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/estudantes/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("the provided state is not valid."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }
}
