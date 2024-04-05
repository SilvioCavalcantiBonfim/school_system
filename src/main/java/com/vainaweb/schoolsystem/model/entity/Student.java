package com.vainaweb.schoolsystem.model.entity;

import java.io.Serializable;

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
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "tb_student")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Student implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank
  private String name;

  @Email
  @NotBlank
  @Column(unique = true)
  private String email;

  @NotBlank
  @CPF
  @Column(unique = true, length = 14)
  private String cpf;

  @Pattern(regexp = "^\\(\\d{2}\\) (9\\d{4}-\\d{4}|\\d{4}-\\d{4})$")
  private String phone;

  @NotNull
  private Course course;

  @Valid
  @NotNull
  private Address address;
}
