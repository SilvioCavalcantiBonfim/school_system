package com.vainaweb.schoolsystem.dto.response;

public record StudentResponse(long id, String name, String email, String cpf, String course, AddressResponse address) {

}
