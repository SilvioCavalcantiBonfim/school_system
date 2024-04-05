package com.vainaweb.schoolsystem.service.collaborator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.vainaweb.schoolsystem.dto.request.CollaboratorRequest;
import com.vainaweb.schoolsystem.dto.request.CollaboratorUpdateRequest;
import com.vainaweb.schoolsystem.dto.response.CollaboratorResponse;

public interface CollaboratorService {

  List<CollaboratorResponse> findAll();

  CollaboratorResponse findById(long id);

  void delete(long id);

  URI create(CollaboratorRequest collaboratorRequest) throws URISyntaxException;

  String update(long id, CollaboratorUpdateRequest collaboratorRequest, String ifMatch) throws IOException;

}