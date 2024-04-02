package com.vainaweb.schoolsystem.dto.response;

public record AddressResponse(
  String zip, 
  String street, 
  Short number, 
  String complement, 
  String city, 
  String state) {

}
