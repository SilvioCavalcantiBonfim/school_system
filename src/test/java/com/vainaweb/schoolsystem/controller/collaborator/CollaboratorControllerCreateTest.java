package com.vainaweb.schoolsystem.controller.collaborator;

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
public class CollaboratorControllerCreateTest {

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
        body.put("name", "Carlos Pereira");
        body.put("email", "carlos.pereira@example.com");
        body.put("cpf", "711.637.750-61");
        body.put("role", "FACILITADOR");

        Map<String, Object> address = new HashMap<>();

        address.put("zip", "45678-123");
        address.put("street", "Rua das Margaridas");
        address.put("number", 3);
        address.put("complement", "Casa 789");
        address.put("city", "Salvador");
        address.put("state", "BA");

        body.put("address", address);
    }

    @Test
    @DisplayName("Create Collaborator Success")
    public void createCollaborator() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/colaboradores/3"));
    }

    @Test
    @DisplayName("Create Collaborator With Already Email")
    public void createCollaboratorAlreadyEmail() throws Exception {

        body.put("email", "joao.silva@example.com");

        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("email address already registered"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator With Already Cpf")
    public void createCollaboratorAlreadyCpf() throws Exception {

        body.put("cpf", "508.206.000-75");

        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("cpf already registered"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    // --------------------------------- name ---------------------------------
    @Test
    @DisplayName("Create Collaborator with name null")
    public void createCollaboratorWithNameNull() throws Exception {
        body.put("name", null);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.name").value("must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with name is blank")
    public void createCollaboratorWithNameBlank() throws Exception {
        body.put("name", "");
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.name").value("must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    // --------------------------------- email ---------------------------------
    @Test
    @DisplayName("Create Collaborator with email null")
    public void createCollaboratorWithEmailNull() throws Exception {
        body.put("email", null);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.email").value("must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with email is blank")
    public void createCollaboratorWithEmailBlank() throws Exception {
        body.put("email", "");
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.email").value("must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with email is not email format")
    public void createCollaboratorWithEmailNotFormatEmail() throws Exception {
        body.put("email", "teste");
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.message.email").value("must be a well-formed email address"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    // --------------------------------- CPF ---------------------------------
    @Test
    @DisplayName("Create Collaborator with CPF null")
    public void createCollaboratorWithCPFNull() throws Exception {
        body.put("cpf", null);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.cpf").value("must not be null"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with CPF is blank")
    public void createCollaboratorWithCPFBlank() throws Exception {
        body.put("cpf", "");
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.cpf").value("invalid Brazilian individual taxpayer registry number (CPF)"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with CPF is invalid format")
    public void createCollaboratorWithCPFisInvalidFormat() throws Exception {
        body.put("cpf", "123.456.78901");
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.cpf")
                        .value("invalid Brazilian individual taxpayer registry number (CPF)"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with CPF is invalid")
    public void createCollaboratorWithCPFisInvalid() throws Exception {
        body.put("cpf", "711.637.750-62");
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.cpf")
                        .value("invalid Brazilian individual taxpayer registry number (CPF)"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    // --------------------------------- role ---------------------------------
    @Test
    @DisplayName("Create Collaborator with role null")
    public void createCollaboratorWithRoleNull() throws Exception {
        body.put("role", null);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.role").value("must not be null"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with role is invalid")
    public void createCollaboratorWithRoleBlank() throws Exception {
        body.put("role", "");
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("the provided role is not valid."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    // --------------------------------- address ---------------------------------
    @Test
    @DisplayName("Create Collaborator with address null")
    public void createCollaboratorWithAddressNull() throws Exception {
        body.put("address", null);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.address").value("must not be null"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with zip null")
    public void createCollaboratorWithZipNull() throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        address.put("zip", null);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.zip").value("must not be null"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with zip invalid format")
    public void createCollaboratorWithZipInvalidFormat() throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        address.put("zip", "teste");

        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.zip").value("must match #####-###"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with street null")
    public void createCollaboratorWithStreetNull() throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        address.put("street", null);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.street").value("must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with street blank")
    public void createCollaboratorWithStreetBlank() throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        address.put("street", "");

        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.street").value("must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with street number is null")
    public void createCollaboratorWithNumberNull() throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        address.put("number", null);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.number").value("must not be null"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with street number is negative")
    public void createCollaboratorWithNumberIsNegative() throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        address.put("number", -1);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.message.number").value("must be greater than or equal to 0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with street number invalid format")
    public void createCollaboratorWithNumberInvalidFormat() throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        address.put("number", "a");

        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("the HTTP request contains malformed syntax or cannot be understood by the server."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with city blank")
    public void createCollaboratorWithCityBlank() throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        address.put("city", "");

        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.city").value("must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with street city is null")
    public void createCollaboratorWithCityNull() throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        address.put("city", null);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message.city").value("must not be blank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Create Collaborator with state city is invalid")
    public void createCollaboratorWithStateInvalid() throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        address.put("state", "teste");

        mockMvc
                .perform(MockMvcRequestBuilders.post("/colaboradores").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("the provided state is not valid."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }
}
