package com.vainaweb.schoolsystem.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.vainaweb.schoolsystem.dto.request.CollaboratorRequest;
import com.vainaweb.schoolsystem.dto.response.CollaboratorResponse;
import com.vainaweb.schoolsystem.exception.CollaboratorNotFoundException;
import com.vainaweb.schoolsystem.model.entity.Collaborator;
import com.vainaweb.schoolsystem.model.mapper.CollaboratorMapper;
import com.vainaweb.schoolsystem.repository.CollaboratorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaboratorServiceImpl implements CollaboratorService {

  private final CollaboratorRepository collaboratorRepository;
  private final CollaboratorMapper collaboratorMapper;

  @Override
  public List<CollaboratorResponse> findAll() {
    return collaboratorRepository.findAll().stream().map(collaboratorMapper::toResponse).toList();
  }

  @Override
  public CollaboratorResponse findById(long id) {
    return collaboratorRepository.findById(id).map(collaboratorMapper::toResponse)
        .orElseThrow(CollaboratorNotFoundException::new);
  }

  @Override
  public void delete(long id) {
    Collaborator collaborator = collaboratorRepository.findById(id).orElseThrow(CollaboratorNotFoundException::new);
    if (Objects.nonNull(collaborator)) {
      collaboratorRepository.delete(collaborator);
    }
  }

  @Override
  public URI create(CollaboratorRequest collaboratorRequest) throws URISyntaxException {
    Collaborator collaborator = collaboratorRepository.save(collaboratorMapper.toEntity(collaboratorRequest));
    URI location = new URI(String.format("/colaboradores/%d", collaborator.getId()));
    return location;
  }

}
