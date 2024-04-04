package com.vainaweb.schoolsystem.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vainaweb.schoolsystem.dto.request.CollaboratorRequest;
import com.vainaweb.schoolsystem.dto.request.CollaboratorUpdateRequest;
import com.vainaweb.schoolsystem.dto.response.CollaboratorResponse;

public interface CollaboratorService {

  List<CollaboratorResponse> findAll();

  CollaboratorResponse findById(long id);

  void delete(long id);

  URI create(CollaboratorRequest collaboratorRequest) throws URISyntaxException;

  String update(long id, CollaboratorUpdateRequest collaboratorRequest, String ifMatch) throws JsonProcessingException;

}