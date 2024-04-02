package com.vainaweb.schoolsystem.service;

import java.util.List;

import com.vainaweb.schoolsystem.dto.response.CollaboratorResponse;

public interface CollaboratorService {

  List<CollaboratorResponse> findAll();

  CollaboratorResponse findById(long id);

  void delete(long id);

}