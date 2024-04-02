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

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@DirtiesContext
public class CollaboratorControllerDeleteTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EntityManager entityManager;

  @Test
  @Transactional
  @SuppressWarnings("null")
  @DisplayName("Delete Collaborator Success")
  public void deleteCollaborator() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.delete("/colaboradores/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNoContent());

    entityManager.flush();
    entityManager.clear();
  }

  @Test
  @SuppressWarnings("null")
  @DisplayName("Delete Collaborator Not Found")
  public void DeleteCollaboratorNotFound() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.delete("/colaboradores/3").contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @SuppressWarnings("null")
  @DisplayName("delete Collaborator Non Numeric Id")
  public void deleteCollaboratorNonNumericId() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.delete("/colaboradores/a").contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("The parameter 'id' is missing.")));
  }
}
