package com.vainaweb.schoolsystem.entity;

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

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

  @Pattern(regexp = "^\\d{5}-\\d{3}$")
  @Column(length = 9)
  private String zip;

  @NotBlank
  private String street;

  @PositiveOrZero
  private Short number;

  private String complement;

  @NotBlank
  private String city;

  @NotNull
  private State state;
}
