package com.vainaweb.schoolsystem.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import java.util.zip.CRC32;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vainaweb.schoolsystem.dto.request.CollaboratorRequest;
import com.vainaweb.schoolsystem.dto.request.CollaboratorUpdateRequest;
import com.vainaweb.schoolsystem.dto.response.CollaboratorResponse;
import com.vainaweb.schoolsystem.exception.CollaboratorNotFoundException;
import com.vainaweb.schoolsystem.model.entity.Collaborator;
import com.vainaweb.schoolsystem.model.mapper.CollaboratorMapper;
import com.vainaweb.schoolsystem.repository.CollaboratorRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaboratorServiceImpl implements CollaboratorService {

  private final CollaboratorRepository collaboratorRepository;
  private final CollaboratorMapper collaboratorMapper;
  private final ObjectMapper objectMapper;
  private final CRC32 crc32;

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

  @Override
  public String update(long id, CollaboratorUpdateRequest collaboratorRequest, String ifMatch)
      throws JsonProcessingException {
    Collaborator collaborator = collaboratorRepository.findById(id)
        .orElseThrow(() -> new CollaboratorNotFoundException());
    System.out.println(collaborator);
    if (Objects.nonNull(ifMatch)) {
      String etag = calculateEtag(collaborator);
      if (!etag.equals(ifMatch)) {

      }
    }
    Collaborator collaboratorMerged = collaboratorMapper.merge(collaborator, collaboratorRequest);

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    Set<ConstraintViolation<Collaborator>> violations = validator.validate(collaboratorMerged);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }

    Collaborator collaboratorUpdated = collaboratorRepository.save(collaboratorMerged);
    return calculateEtag(collaboratorUpdated);
  }

  private String calculateEtag(Collaborator collaborator) throws JsonProcessingException {
    crc32.reset();
    crc32.update(objectMapper.writeValueAsString(collaborator).getBytes());
    return String.format("%x", crc32.getValue());
  }
}
