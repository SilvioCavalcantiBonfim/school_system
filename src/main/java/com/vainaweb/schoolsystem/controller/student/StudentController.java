package com.vainaweb.schoolsystem.controller.student;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vainaweb.schoolsystem.dto.response.StudentResponse;

public interface StudentController {

  @GetMapping("/todos")
  ResponseEntity<List<StudentResponse>> findAll();

  @GetMapping("/{id}")
  ResponseEntity<StudentResponse> findById(@PathVariable long id);

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteById(@PathVariable long id);
}
