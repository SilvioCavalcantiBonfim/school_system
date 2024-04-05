package com.vainaweb.schoolsystem.dto.response;

import lombok.Builder;

@Builder
public record AddressResponse(
  String zip, 
  String street, 
  Short number, 
  String complement, 
  String city, 
  String state) {

}
