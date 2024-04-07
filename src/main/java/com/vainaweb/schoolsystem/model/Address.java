package com.vainaweb.schoolsystem.model;

import java.io.Serializable;

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
public class Address implements Serializable {

  @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "must match #####-###")
  @NotNull(message = "must not be null")
  @Column(length = 9)
  private String zip;

  @NotBlank(message = "must not be blank")
  private String street;

  @NotNull(message = "must not be null")
  @PositiveOrZero(message = "must be greater than or equal to 0")
  private Short number;

  private String complement;

  @NotBlank(message = "must not be blank")
  private String city;

  @NotNull(message = "must not be null")
  private State state;
}
