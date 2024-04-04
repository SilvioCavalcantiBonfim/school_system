package com.vainaweb.schoolsystem.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vainaweb.schoolsystem.dto.request.CollaboratorRequest;
import com.vainaweb.schoolsystem.dto.request.CollaboratorUpdateRequest;
import com.vainaweb.schoolsystem.dto.response.CollaboratorResponse;
import com.vainaweb.schoolsystem.service.CollaboratorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/colaboradores")
class CollaboratorControllerImpl implements CollaboratorController {

  private final CollaboratorService collaboratorService;

  @Override
  public ResponseEntity<List<CollaboratorResponse>> findAll() {
    return ResponseEntity.ok().body(collaboratorService.findAll());
  }

  @Override
  public ResponseEntity<CollaboratorResponse> findById(long id) {
    return ResponseEntity.ok().body(collaboratorService.findById(id));
  }

  @Override
  public ResponseEntity<Void> deleteById(long id) {
    collaboratorService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> create(CollaboratorRequest collaboratorRequest) throws URISyntaxException {
    URI location = collaboratorService.create(collaboratorRequest);
    return ResponseEntity.created(location).build();
  }

  @Override
  public ResponseEntity<Void> update(long id, CollaboratorUpdateRequest collaboratorRequest, String ifMatch) throws JsonProcessingException {
    String etag = collaboratorService.update(id, collaboratorRequest, ifMatch);
    return ResponseEntity.noContent().eTag(etag).build();
  }
  
}
