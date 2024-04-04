package com.vainaweb.schoolsystem.model.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.vainaweb.schoolsystem.dto.request.AddressRequest;
import com.vainaweb.schoolsystem.dto.response.AddressResponse;
import com.vainaweb.schoolsystem.exception.IllegalStateStringException;
import com.vainaweb.schoolsystem.model.entity.Address;
import com.vainaweb.schoolsystem.model.entity.Address.AddressBuilder;
import com.vainaweb.schoolsystem.model.entity.State;

@Component
public class AddressMapper implements Mapper<Address, AddressResponse, AddressRequest> {

  @Override
  public AddressResponse toResponse(Address address) {
    return new AddressResponse(address.getZip(), address.getStreet(), address.getNumber(), address.getComplement(),
        address.getCity(), address.getState().toString());
  }

  @Override
  public Address toEntity(AddressRequest addressRequest) {
    if (Objects.isNull(addressRequest)) {
      return null;
    }

    AddressBuilder address = Address.builder()
      .zip(addressRequest.zip())
      .city(addressRequest.city())
      .complement(addressRequest.complement())
      .street(addressRequest.street())
      .number(addressRequest.number());

      if (Objects.nonNull(addressRequest.state())){
        try {
          address.state(State.valueOf(addressRequest.state().toUpperCase()));
        } catch (Exception e) {
          throw new IllegalStateStringException("the provided state is not valid.");
        }
      }
    return address.build();
  }
}
