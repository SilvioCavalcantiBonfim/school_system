package com.vainaweb.schoolsystem.dto.request;

public record StudentRequest(String name, String email, String cpf, String phone, String course,
    AddressRequest address) {
}
