package com.vainaweb.schoolsystem.component.mapper;

import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

import com.vainaweb.schoolsystem.dto.request.CollaboratorRequest;
import com.vainaweb.schoolsystem.dto.request.CollaboratorUpdateRequest;
import com.vainaweb.schoolsystem.dto.response.CollaboratorResponse;
import com.vainaweb.schoolsystem.exception.IllegalRoleException;
import com.vainaweb.schoolsystem.model.Collaborator;
import com.vainaweb.schoolsystem.model.Role;
import com.vainaweb.schoolsystem.model.Collaborator.CollaboratorBuilder;
import com.vainaweb.schoolsystem.service.obfuscator.ObfuscatorService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CollaboratorMapper implements Mapper<Collaborator, CollaboratorResponse, CollaboratorRequest> {

  private final AddressMapper addressMapper;
  private final ObfuscatorService obfuscatorService;

  @Override
  public CollaboratorResponse toResponse(Collaborator collaborator) {
    return CollaboratorResponse.builder()
        .id(collaborator.getId())
        .name(collaborator.getName())
        .email(collaborator.getEmail())
        .cpf(obfuscatorService.obfuscateCpf(collaborator.getCpf()))
        .role(collaborator.getRole().toString())
        .address(addressMapper.toResponse(collaborator.getAddress()))
        .build();
  }

  @Override
  public Collaborator toEntity(CollaboratorRequest collaboratorRequest) {
    CollaboratorBuilder collaborator = Collaborator.builder()
        .name(collaboratorRequest.name())
        .email(collaboratorRequest.email())
        .cpf(collaboratorRequest.cpf())
        .address(addressMapper.toEntity(collaboratorRequest.address()));

    if (Objects.nonNull(collaboratorRequest.role())) {
      try {
        collaborator.role(Role.valueOf(collaboratorRequest.role().toUpperCase()));
      } catch (Exception e) {
        throw new IllegalRoleException();
      }
    }

    return collaborator.build();
  }

  public Collaborator merge(Collaborator entity, CollaboratorUpdateRequest request) {
    Optional.ofNullable(request.name()).ifPresent(entity::setName);
    Optional.ofNullable(request.email()).ifPresent(entity::setEmail);
    Optional.ofNullable(request.role()).ifPresent(role -> {
      try {
        entity.setRole(Role.valueOf(role.toUpperCase()));
      } catch (Exception e) {
        throw new IllegalRoleException();
      }
    });
    Optional.ofNullable(request.address())
        .ifPresent(addressRequest -> entity.setAddress(addressMapper.merge(entity.getAddress(), addressRequest)));

    return entity;
  }
}
