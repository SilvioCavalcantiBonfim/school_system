package com.vainaweb.schoolsystem.model.entity;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_collaborator")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Collaborator {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  @NotBlank
  private String name;
  
  @Email
  @NotNull
  @NotBlank
  @Column(unique = true)
  private String email;
  
  @NotNull
  @NotBlank
  @CPF
  @Column(unique = true, length = 14)
  private String cpf;
  
  @NotNull
  private Role role;
  
  @Valid
  @NotNull
  private Address address;
}
