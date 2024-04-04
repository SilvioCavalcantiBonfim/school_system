package com.vainaweb.schoolsystem.model.mapper;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.vainaweb.schoolsystem.dto.request.CollaboratorRequest;
import com.vainaweb.schoolsystem.dto.request.CollaboratorUpdateRequest;
import com.vainaweb.schoolsystem.dto.response.CollaboratorResponse;
import com.vainaweb.schoolsystem.exception.IllegalRoleException;
import com.vainaweb.schoolsystem.model.entity.Collaborator;
import com.vainaweb.schoolsystem.model.entity.Collaborator.CollaboratorBuilder;
import com.vainaweb.schoolsystem.model.entity.Role;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CollaboratorMapper implements Mapper<Collaborator, CollaboratorResponse, CollaboratorRequest> {

  private final AddressMapper addressMapper;

  @Override
  public CollaboratorResponse toResponse(Collaborator collaborator) {
    return new CollaboratorResponse(collaborator.getName(), collaborator.getEmail(),
        cpfObfuscate(collaborator.getCpf()),
        collaborator.getRole().toString(), addressMapper.toResponse(collaborator.getAddress()));
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

  private String cpfObfuscate(String cpf) {
    Pattern pattern = Pattern.compile("(\\d{3})\\.(\\d{3})\\.(\\d{3})-(\\d{2})");
    Matcher matcher = pattern.matcher(cpf);

    return matcher.replaceAll("$1.***.***-$4");
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
