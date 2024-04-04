package com.vainaweb.schoolsystem.dto.request;

public record CollaboratorUpdateRequest(String name, String email, String role, AddressRequest address) {
}
