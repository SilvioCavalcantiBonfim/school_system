package com.vainaweb.schoolsystem.dto.response;

public record CollaboratorResponse(long id, String name, String email, String cpf, String role, AddressResponse address) {

}
