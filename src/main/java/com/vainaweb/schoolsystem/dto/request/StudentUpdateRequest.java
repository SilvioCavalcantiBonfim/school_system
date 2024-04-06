package com.vainaweb.schoolsystem.dto.request;

public record StudentUpdateRequest(String name, String phone, String course,
AddressRequest address) {
}
