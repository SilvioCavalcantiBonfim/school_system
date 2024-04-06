package com.vainaweb.schoolsystem.controller.student;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.vainaweb.schoolsystem.dto.request.StudentRequest;
import com.vainaweb.schoolsystem.dto.request.StudentUpdateRequest;
import com.vainaweb.schoolsystem.dto.response.StudentResponse;

public interface StudentController {

  @GetMapping
  ResponseEntity<List<StudentResponse>> findAll();

  @GetMapping("/{id}")
  ResponseEntity<StudentResponse> findById(@PathVariable long id);

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteById(@PathVariable long id);

  @PostMapping
  ResponseEntity<Void> create(@RequestBody StudentRequest request) throws URISyntaxException;

  @PutMapping("/{id}")
  ResponseEntity<Void> update(@PathVariable final long id, @RequestBody final StudentUpdateRequest collaboratorRequest,
      @RequestHeader(name = "If-Match", required = false) final String ifMatch) throws IOException;
}
