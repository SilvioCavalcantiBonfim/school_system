package com.vainaweb.schoolsystem.util.mapper;

import org.springframework.stereotype.Component;

import com.vainaweb.schoolsystem.dto.request.AddressRequest;
import com.vainaweb.schoolsystem.dto.response.AddressResponse;
import com.vainaweb.schoolsystem.entity.Address;

@Component
public class AddressMapper implements Mapper<Address, AddressResponse, AddressRequest> {

  @Override
  public AddressResponse toResponse(Address address) {
    return new AddressResponse(address.getZip(), address.getStreet(), address.getNumber(), address.getComplement(),
        address.getCity(), address.getState().toString());
  }

  @Override
  public Address toEntity(AddressRequest request) {
    throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
  }
}
