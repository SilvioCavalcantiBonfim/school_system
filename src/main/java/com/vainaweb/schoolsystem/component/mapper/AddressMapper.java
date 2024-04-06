package com.vainaweb.schoolsystem.component.mapper;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.vainaweb.schoolsystem.dto.request.AddressRequest;
import com.vainaweb.schoolsystem.dto.response.AddressResponse;
import com.vainaweb.schoolsystem.model.Address;
import com.vainaweb.schoolsystem.model.State;
import com.vainaweb.schoolsystem.model.Address.AddressBuilder;

@Component
public class AddressMapper implements Mapper<Address, AddressResponse, AddressRequest> {

  @Override
  public AddressResponse toResponse(Address address) {
    return AddressResponse.builder()
        .zip(address.getZip())
        .street(address.getStreet())
        .number(address.getNumber())
        .complement(address.getComplement())
        .city(address.getCity())
        .state(address.getState().toString())
        .build();
  }

  @Override
  public Address toEntity(AddressRequest request) {
    if (Objects.isNull(request)) {
      return null;
    }

    AddressBuilder address = Address.builder()
        .zip(request.zip())
        .city(request.city())
        .complement(request.complement())
        .street(request.street())
        .number(request.number());

    Optional.ofNullable(request.state()).map(State::of).ifPresent(address::state);

    return address.build();
  }

  public Address merge(Address entity, AddressRequest request) {
    Optional.ofNullable(request.zip()).ifPresent(entity::setZip);
    Optional.ofNullable(request.street()).ifPresent(entity::setStreet);
    Optional.ofNullable(request.city()).ifPresent(entity::setCity);
    Optional.ofNullable(request.number()).ifPresent(entity::setNumber);
    Optional.ofNullable(request.complement()).ifPresent(entity::setComplement);
    Optional.ofNullable(request.state()).map(State::of).ifPresent(entity::setState);

    return entity;
  }
}
