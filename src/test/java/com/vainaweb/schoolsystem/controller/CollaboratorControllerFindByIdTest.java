package com.vainaweb.schoolsystem.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@DirtiesContext
public class CollaboratorControllerFindByIdTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Find By Id Collaborator Success")
  public void findAllCollaborator() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/colaboradores/1").contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("João Silva")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("joao.silva@example.com")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", Matchers.is("508.***.***-75")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.role", Matchers.is("FACILITADOR")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address.zip", Matchers.is("12345-678")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address.street", Matchers.is("Rua das Flores")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address.number", Matchers.is(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address.complement", Matchers.is("Apto 123")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address.city", Matchers.is("São Paulo")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address.state", Matchers.is("SP")));
  }

  
  @Test
  @DisplayName("Find By Id Collaborator Not Found")
  public void findAllCollaboratorNotFound() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/colaboradores/3").contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @DisplayName("Find By Id Collaborator Non Numeric Id")
  public void findAllCollaboratorNonNumericId() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/colaboradores/a").contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("The parameter 'id' is missing.")));
  }
}
