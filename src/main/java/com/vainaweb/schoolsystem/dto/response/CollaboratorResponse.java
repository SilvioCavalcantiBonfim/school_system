package com.vainaweb.schoolsystem.dto.response;

import lombok.Builder;

@Builder
public record CollaboratorResponse(long id, String name, String email, String cpf, String role, AddressResponse address) {

}
