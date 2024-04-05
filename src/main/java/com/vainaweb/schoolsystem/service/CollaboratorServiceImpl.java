package com.vainaweb.schoolsystem.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.vainaweb.schoolsystem.dto.request.CollaboratorRequest;
import com.vainaweb.schoolsystem.dto.request.CollaboratorUpdateRequest;
import com.vainaweb.schoolsystem.dto.response.CollaboratorResponse;
import com.vainaweb.schoolsystem.exception.AlreadyCpfException;
import com.vainaweb.schoolsystem.exception.AlreadyEmailException;
import com.vainaweb.schoolsystem.exception.CollaboratorNotFoundException;
import com.vainaweb.schoolsystem.exception.ETagMismatchException;
import com.vainaweb.schoolsystem.model.entity.Collaborator;
import com.vainaweb.schoolsystem.model.mapper.CollaboratorMapper;
import com.vainaweb.schoolsystem.repository.CollaboratorRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaboratorServiceImpl implements CollaboratorService {

  private final CollaboratorRepository collaboratorRepository;
  private final CollaboratorMapper collaboratorMapper;
  private final ETagService eTagService;

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
    collaboratorRepository.delete(collaborator);
  }

  @Override
  @Valid
  public URI create(CollaboratorRequest collaboratorRequest) throws URISyntaxException {
    Collaborator collaborator = collaboratorMapper.toEntity(collaboratorRequest);
    checkEmailAlreadyExists(collaborator.getEmail());
    checkCpfAlreadyExists(collaborator.getCpf());
    collaborator = saveCollaborator(collaborator);
    URI location = new URI(String.format("/colaboradores/%d", collaborator.getId()));
    return location;
  }

  private void checkCpfAlreadyExists(String cpf){
    Optional.ofNullable(cpf).ifPresent(e -> {
      if (collaboratorRepository.existsByCpf(cpf)) {
        throw new AlreadyCpfException();
      }
    });
  }

  @Override
  public String update(long id, CollaboratorUpdateRequest collaboratorRequest, String ifMatch)
      throws IOException {
    checkEmailAlreadyExists(collaboratorRequest.email());
    Collaborator collaborator = findCollaboratorById(id);
    validateETag(ifMatch, collaborator);
    Collaborator collaboratorMerged = mergeCollaboratorData(collaboratorRequest, collaborator);
    validateCollaborator(collaboratorMerged);
    Collaborator collaboratorUpdated = saveCollaborator(collaboratorMerged);
    return generateEtag(collaboratorUpdated);
  }

  private void checkEmailAlreadyExists(String email) {
    Optional.ofNullable(email).ifPresent(e -> {
      if (collaboratorRepository.existsByEmail(email)) {
        throw new AlreadyEmailException();
      }
    });
  }

  private Collaborator findCollaboratorById(long id) {
    return collaboratorRepository.findById(id)
        .orElseThrow(() -> new CollaboratorNotFoundException());
  }

  private void validateETag(String ifMatch, Collaborator collaborator) throws IOException {
    if (Objects.nonNull(ifMatch)) {
      String etag = generateEtag(collaborator);
      System.out.println(etag);
      if (!etag.equals(ifMatch)) {
        throw new ETagMismatchException();
      }
    }
  }

  private String generateEtag(Collaborator collaborator) throws IOException {
    return String.format("%x", eTagService.generate(collaborator));
  }

  private Collaborator mergeCollaboratorData(CollaboratorUpdateRequest collaboratorRequest, Collaborator collaborator) {
    return collaboratorMapper.merge(collaborator, collaboratorRequest);
  }

  private void validateCollaborator(Collaborator collaborator) {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    Set<ConstraintViolation<Collaborator>> violations = validator.validate(collaborator);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private Collaborator saveCollaborator(Collaborator collaboratorMerged) {
    return collaboratorRepository.save(collaboratorMerged);
  }

}
