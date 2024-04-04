package com.vainaweb.schoolsystem.model.mapper;

import java.util.Objects;
import java.util.Optional;

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

    if (Objects.nonNull(request.state())) {
      try {
        address.state(State.valueOf(request.state().toUpperCase()));
      } catch (Exception e) {
        throw new IllegalStateStringException();
      }
    }
    return address.build();
  }

  public Address merge(Address entity, AddressRequest request) {
    Optional.ofNullable(request.zip()).ifPresent(entity::setZip);
    Optional.ofNullable(request.street()).ifPresent(entity::setStreet);
    Optional.ofNullable(request.city()).ifPresent(entity::setCity);
    Optional.ofNullable(request.number()).ifPresent(entity::setNumber);
    Optional.ofNullable(request.complement()).ifPresent(entity::setComplement);
    Optional.ofNullable(request.state()).ifPresent(state -> {
      try {
        entity.setState(State.valueOf(state.toUpperCase()));
      } catch (Exception e) {
        throw new IllegalStateStringException();
      }
    });

    return entity;
  }
}
