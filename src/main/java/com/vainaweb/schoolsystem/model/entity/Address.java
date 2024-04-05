package com.vainaweb.schoolsystem.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Address {

  @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "must match #####-###")
  @NotNull
  @Column(length = 9)
  private String zip;

  @NotBlank
  private String street;

  @NotNull
  @PositiveOrZero
  private Short number;

  private String complement;

  @NotBlank
  private String city;

  @NotNull
  private State state;
}
