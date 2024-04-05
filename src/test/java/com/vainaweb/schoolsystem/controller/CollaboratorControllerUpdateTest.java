package com.vainaweb.schoolsystem.controller;

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

// import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@DirtiesContext
public class CollaboratorControllerUpdateTest {

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
  @DisplayName("Update Collaborator Not Found")
  public void updateCollaboratorNotFound() throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.put("/colaboradores/3")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @DisplayName("Update Collaborator Non Numeric Id")
  public void updateCollaboratorNonNumericId() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.put("/colaboradores/a")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("The parameter 'id' is missing.")));
  }

  @Test
  @DisplayName("Name Update Collaborator Success")
  public void nameUpdateCollaborator() throws Exception {

    body.put("name", "João da Silva");

    mockMvc.perform(MockMvcRequestBuilders.put("/colaboradores/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("If-Match", "bcc6aae2"))
        .andExpect(MockMvcResultMatchers.status().isNoContent())
        .andExpect(MockMvcResultMatchers.header().string("ETag", Matchers.containsString("ff794c6")));
  }

  @Test
  @DisplayName("Name Update Collaborator If-Match non match")
  public void nameUpdateCollaboratorNonMatch() throws Exception {

    body.put("name", "João da Silva");

    mockMvc.perform(MockMvcRequestBuilders.put("/colaboradores/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("If-Match", "a511fdad"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("the received ETag does not match the current ETag"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Name Update Collaborator is blank")
  public void nameUpdateCollaboratorIsBlank() throws Exception {

    body.put("name", "");

    mockMvc.perform(MockMvcRequestBuilders.put("/colaboradores/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("If-Match", "bcc6aae2"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.name").value("must not be blank"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Email Update Collaborator is blank")
  public void emailUpdateCollaboratorIsBlank() throws Exception {

    body.put("email", "");

    mockMvc.perform(MockMvcRequestBuilders.put("/colaboradores/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("If-Match", "bcc6aae2"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.email").value("must not be blank"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Email Update Collaborator is already")
  public void emailUpdateCollaboratorIsAlready() throws Exception {

    body.put("email", "joao.silva@example.com");

    mockMvc.perform(MockMvcRequestBuilders.put("/colaboradores/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("If-Match", "bcc6aae2"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("email address already registered"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Email Update Collaborator is invalid format")
  public void emailUpdateCollaboratorIsInvalidFormat() throws Exception {

    body.put("email", "teste");

    mockMvc.perform(MockMvcRequestBuilders.put("/colaboradores/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("If-Match", "bcc6aae2"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.email").value("must be a well-formed email address"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Create Collaborator with role is invalid")
  public void createCollaboratorWithRoleBlank() throws Exception {
    body.put("role", "");
    mockMvc.perform(MockMvcRequestBuilders.put("/colaboradores/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("If-Match", "bcc6aae2"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("the provided role is not valid."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Create Collaborator with zip invalid format")
  public void createCollaboratorWithZipInvalidFormat() throws Exception {

    Map<String, Object> address = new HashMap<>();
    address.put("zip", "teste");
    body.put("address", address);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/colaboradores/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.zip").value("must match #####-###"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Update Collaborator with street invalid format")
  public void updateCollaboratorWithStreetInvalidFormat() throws Exception {

    Map<String, Object> address = new HashMap<>();
    address.put("street", "");
    body.put("address", address);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/colaboradores/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.street").value("must not be blank"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Update Collaborator with number invalid")
  public void updateCollaboratorWithNumberInvalid() throws Exception {

    Map<String, Object> address = new HashMap<>();
    address.put("number", -1);
    body.put("address", address);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/colaboradores/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.number").value("must be greater than or equal to 0"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Update Collaborator with number is non number")
  public void updateCollaboratorWithNumberNonNumber() throws Exception {

    Map<String, Object> address = new HashMap<>();
    address.put("number", "a");
    body.put("address", address);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/colaboradores/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .value("the HTTP request contains malformed syntax or cannot be understood by the server."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Update Collaborator with city is blank")
  public void updateCollaboratorWithCityBlank() throws Exception {

    Map<String, Object> address = new HashMap<>();
    address.put("city", "");
    body.put("address", address);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/colaboradores/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message.city").value("must not be blank"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("update Collaborator with state city is invalid")
  public void updateCollaboratorWithStateInvalid() throws Exception {

    Map<String, Object> address = new HashMap<>();
    address.put("state", "teste");
    body.put("address", address);

    mockMvc
        .perform(MockMvcRequestBuilders.put("/colaboradores/1").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(body)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("the provided state is not valid."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
  }
}
