package com.vainaweb.schoolsystem.dto.request;

public record CollaboratorRequest(String name, String email, String cpf, String role, AddressRequest address) {
}
