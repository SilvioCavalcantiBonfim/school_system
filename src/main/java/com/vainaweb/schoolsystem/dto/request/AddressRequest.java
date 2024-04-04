package com.vainaweb.schoolsystem.dto.request;

public record AddressRequest(
    String zip,
    String street,
    Short number,
    String complement,
    String city,
    String state) {}
