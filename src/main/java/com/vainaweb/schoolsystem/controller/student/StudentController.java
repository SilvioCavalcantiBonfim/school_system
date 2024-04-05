package com.vainaweb.schoolsystem.controller.student;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.vainaweb.schoolsystem.dto.response.StudentResponse;

public interface StudentController {
  @GetMapping("/todos")
  ResponseEntity<List<StudentResponse>> findAll();
}
