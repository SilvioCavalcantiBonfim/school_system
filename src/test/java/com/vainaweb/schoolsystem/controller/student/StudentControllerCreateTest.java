package com.vainaweb.schoolsystem.controller.student;

import java.util.HashMap;
import java.util.Map;

import org.flywaydb.core.Flyway;
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
public class StudentControllerCreateTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Autowired
        private Flyway flyway;

        private final Map<String, Object> body = new HashMap<>();

        @BeforeEach
        public void setup() {

                jdbcTemplate.execute("DROP ALL OBJECTS DELETE FILES");

                flyway.migrate();

                body.clear();
                body.put("name", "Alice Johnson");
                body.put("email", "alicejohnson@example.com");
                body.put("cpf", "740.276.080-46");
                body.put("phone", "(44) 95432-1098");
                body.put("course", "MOBILE");

                Map<String, Object> address = new HashMap<>();

                address.put("zip", "45678-901");
                address.put("street", "Rua das Estrelas");
                address.put("number", 400);
                address.put("complement", "Casa 40");
                address.put("city", "Salvador");
                address.put("state", "BA");

                body.put("address", address);

        }

        @Test
        @DisplayName("Create Student Success")
        public void createStudent() throws Exception {
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(MockMvcResultMatchers.header().string("Location", "/estudantes/4"));
        }

        @Test
        @DisplayName("Create Student With Already Email")
        public void createStudentAlreadyEmail() throws Exception {

                body.put("email", "janesmith@example.com");

                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                                                .value("email address already registered"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student With Already Cpf")
        public void createStudentAlreadyCpf() throws Exception {

                body.put("cpf", "750.260.520-70");

                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("cpf already registered"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        // --------------------------------- name ---------------------------------
        @Test
        @DisplayName("Create Student with name null")
        public void createStudentWithNameNull() throws Exception {
                body.put("name", null);
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.name").value("must not be blank"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with name is blank")
        public void createStudentWithNameBlank() throws Exception {
                body.put("name", "");
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.name").value("must not be blank"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        // --------------------------------- email ---------------------------------
        @Test
        @DisplayName("Create Student with email null")
        public void createStudentWithEmailNull() throws Exception {
                body.put("email", null);
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.email").value("must not be blank"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with email is blank")
        public void createStudentWithEmailBlank() throws Exception {
                body.put("email", "");
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.email").value("must not be blank"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with email is not email format")
        public void createStudentWithEmailNotFormatEmail() throws Exception {
                body.put("email", "teste");
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.message.email")
                                                                .value("must be a well-formed email address"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        // --------------------------------- CPF ---------------------------------
        @Test
        @DisplayName("Create Student with CPF null")
        public void createStudentWithCPFNull() throws Exception {
                body.put("cpf", null);
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.cpf").value("must not be null"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with CPF is blank")
        public void createStudentWithCPFBlank() throws Exception {
                body.put("cpf", "");
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.cpf")
                                                .value("invalid Brazilian individual taxpayer registry number (CPF)"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with CPF is invalid format")
        public void createStudentWithCPFisInvalidFormat() throws Exception {
                body.put("cpf", "123.456.78901");
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.cpf")
                                                .value("invalid Brazilian individual taxpayer registry number (CPF)"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with CPF is invalid")
        public void createStudentWithCPFisInvalid() throws Exception {
                body.put("cpf", "711.637.750-62");
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.cpf")
                                                .value("invalid Brazilian individual taxpayer registry number (CPF)"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        // --------------------------------- course ---------------------------------
        @Test
        @DisplayName("Create Student with course null")
        public void createStudentWithcourseNull() throws Exception {
                body.put("course", null);
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.course").value("must not be null"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with course is invalid")
        public void createStudentWithcourseBlank() throws Exception {
                body.put("course", "");
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                                                .value("the provided course is not valid."))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        // --------------------------------- address ---------------------------------
        @Test
        @DisplayName("Create Student with address null")
        public void createStudentWithAddressNull() throws Exception {
                body.put("address", null);
                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.address")
                                                .value("must not be null"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with zip null")
        public void createStudentWithZipNull() throws Exception {

                @SuppressWarnings("unchecked")
                Map<String, Object> address = (Map<String, Object>) body.get("address");
                address.put("zip", null);

                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.zip").value("must not be null"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with zip invalid format")
        public void createStudentWithZipInvalidFormat() throws Exception {

                @SuppressWarnings("unchecked")
                Map<String, Object> address = (Map<String, Object>) body.get("address");
                address.put("zip", "teste");

                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.zip")
                                                .value("must match #####-###"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with street null")
        public void createStudentWithStreetNull() throws Exception {

                @SuppressWarnings("unchecked")
                Map<String, Object> address = (Map<String, Object>) body.get("address");
                address.put("street", null);

                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.street")
                                                .value("must not be blank"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with street blank")
        public void createStudentWithStreetBlank() throws Exception {

                @SuppressWarnings("unchecked")
                Map<String, Object> address = (Map<String, Object>) body.get("address");
                address.put("street", "");

                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.street")
                                                .value("must not be blank"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with street number is null")
        public void createStudentWithNumberNull() throws Exception {

                @SuppressWarnings("unchecked")
                Map<String, Object> address = (Map<String, Object>) body.get("address");
                address.put("number", null);

                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.number").value("must not be null"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with street number is negative")
        public void createStudentWithNumberIsNegative() throws Exception {

                @SuppressWarnings("unchecked")
                Map<String, Object> address = (Map<String, Object>) body.get("address");
                address.put("number", -1);

                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.message.number")
                                                                .value("must be greater than or equal to 0"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with street number invalid format")
        public void createStudentWithNumberInvalidFormat() throws Exception {

                @SuppressWarnings("unchecked")
                Map<String, Object> address = (Map<String, Object>) body.get("address");
                address.put("number", "a");

                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                                                .value("the HTTP request contains malformed syntax or cannot be understood by the server."))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with city blank")
        public void createStudentWithCityBlank() throws Exception {

                @SuppressWarnings("unchecked")
                Map<String, Object> address = (Map<String, Object>) body.get("address");
                address.put("city", "");

                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.city").value("must not be blank"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with street city is null")
        public void createStudentWithCityNull() throws Exception {

                @SuppressWarnings("unchecked")
                Map<String, Object> address = (Map<String, Object>) body.get("address");
                address.put("city", null);

                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message.city").value("must not be blank"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("Create Student with state city is invalid")
        public void createStudentWithStateInvalid() throws Exception {

                @SuppressWarnings("unchecked")
                Map<String, Object> address = (Map<String, Object>) body.get("address");
                address.put("state", "teste");

                mockMvc
                                .perform(MockMvcRequestBuilders.post("/estudantes")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(body)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                                                .value("the provided state is not valid."))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
        }
}
