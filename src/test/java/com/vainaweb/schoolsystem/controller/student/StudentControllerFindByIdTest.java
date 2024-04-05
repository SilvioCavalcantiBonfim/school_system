package com.vainaweb.schoolsystem.controller.student;

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
public class StudentControllerFindByIdTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Find By Id Student Success")
  public void findAllStudent() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/estudantes/1").contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("John Doe")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("johndoe@example.com")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", Matchers.is("142.***.***-02")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.phone", Matchers.is("(11) 98765-4321")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.course", Matchers.is("BACKEND")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address.zip", Matchers.is("12345-678")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address.street", Matchers.is("Rua das Flores")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address.number", Matchers.is(100)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address.complement", Matchers.is("Apto 10")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address.city", Matchers.is("São Paulo")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address.state", Matchers.is("SP")));
  }

  
  @Test
  @DisplayName("Find By Id Student Not Found")
  public void findAllStudentNotFound() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/estudantes/4").contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

    
  @Test
  @DisplayName("Find By Id Student Out of Reach")
  public void findAllStudentOutOfReach() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/estudantes/9223372036854775809").contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("The parameter 'id' is missing.")));
  }

  @Test
  @DisplayName("Find By Id Student Non Numeric Id")
  public void findAllStudentNonNumericId() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/estudantes/a").contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(400)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("The parameter 'id' is missing.")));
  }
}
