package com.vainaweb.schoolsystem.service.collaborator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.stereotype.Service;

import com.vainaweb.schoolsystem.component.checked.Checked;
import com.vainaweb.schoolsystem.component.mapper.CollaboratorMapper;
import com.vainaweb.schoolsystem.dto.request.CollaboratorRequest;
import com.vainaweb.schoolsystem.dto.request.CollaboratorUpdateRequest;
import com.vainaweb.schoolsystem.dto.response.CollaboratorResponse;
import com.vainaweb.schoolsystem.exception.CollaboratorNotFoundException;
import com.vainaweb.schoolsystem.model.Collaborator;
import com.vainaweb.schoolsystem.repository.CollaboratorRepository;
import com.vainaweb.schoolsystem.service.etag.ETagService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class CollaboratorServiceImpl implements CollaboratorService {

  private final CollaboratorRepository collaboratorRepository;
  private final CollaboratorMapper collaboratorMapper;
  private final Checked collaboratorChecked;
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
  public URI create(CollaboratorRequest collaboratorRequest) throws URISyntaxException {
    collaboratorChecked.checkEmailOrCpfAlreadyExists(collaboratorRequest.email(), collaboratorRequest.cpf());
    Collaborator collaborator = collaboratorMapper.toEntity(collaboratorRequest);
    collaborator = saveCollaborator(collaborator);
    URI location = new URI(String.format("/colaboradores/%d", collaborator.getId()));
    return location;
  }

  @Override
  public String update(long id, CollaboratorUpdateRequest request, String ifMatch)
      throws IOException {
    collaboratorChecked.checkEmailAlreadyExists(request.email());
    Collaborator collaborator = findCollaboratorById(id);
    eTagService.validate(ifMatch, collaborator);
    collaborator = saveCollaborator(mergeCollaboratorData(request, collaborator));
    return eTagService.generate(collaborator);
  }

  private Collaborator findCollaboratorById(long id) {
    return collaboratorRepository.findById(id)
        .orElseThrow(() -> new CollaboratorNotFoundException());
  }

  private Collaborator mergeCollaboratorData(CollaboratorUpdateRequest collaboratorRequest, Collaborator collaborator) {
    return collaboratorMapper.merge(collaborator, collaboratorRequest);
  }

  private Collaborator saveCollaborator(@Valid Collaborator collaborator) {
    return collaboratorRepository.save(collaborator);
  }

}
