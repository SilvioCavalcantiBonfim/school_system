package com.vainaweb.schoolsystem.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vainaweb.schoolsystem.dto.request.CollaboratorRequest;
import com.vainaweb.schoolsystem.dto.response.CollaboratorResponse;

public interface CollaboratorController {

  @GetMapping("/todos")
  ResponseEntity<List<CollaboratorResponse>> findAll();

  @GetMapping("/{id}")
  ResponseEntity<CollaboratorResponse> findById(@PathVariable long id);

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteById(@PathVariable long id);

  @PostMapping
  ResponseEntity<Void> create(@RequestBody CollaboratorRequest collaboratorRequest) throws URISyntaxException ;
}
