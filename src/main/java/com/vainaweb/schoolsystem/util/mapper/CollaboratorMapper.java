package com.vainaweb.schoolsystem.util.mapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.vainaweb.schoolsystem.dto.request.CollaboratorRequest;
import com.vainaweb.schoolsystem.dto.response.CollaboratorResponse;
import com.vainaweb.schoolsystem.entity.Collaborator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CollaboratorMapper implements Mapper<Collaborator, CollaboratorResponse, CollaboratorRequest> {

  private final AddressMapper addressMapper;

  @Override
  public CollaboratorResponse toResponse(Collaborator collaborator) {
    return new CollaboratorResponse(collaborator.getName(), collaborator.getEmail(), cpfObfuscate(collaborator.getCpf()),
        collaborator.getRole().toString(), addressMapper.toResponse(collaborator.getAddress()));
  }

  @Override
  public Collaborator toEntity(CollaboratorRequest request) {
    throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
  }

  private String cpfObfuscate(String cpf){
    Pattern pattern = Pattern.compile("(\\d{3})\\.(\\d{3})\\.(\\d{3})-(\\d{2})");
    Matcher matcher = pattern.matcher(cpf);

    return matcher.replaceAll("$1.***.***-$4");
  }
}
