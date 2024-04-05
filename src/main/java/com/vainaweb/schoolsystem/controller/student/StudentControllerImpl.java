package com.vainaweb.schoolsystem.controller.student;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vainaweb.schoolsystem.dto.response.StudentResponse;
import com.vainaweb.schoolsystem.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("estudantes")
@RequiredArgsConstructor
public class StudentControllerImpl implements StudentController {

  private final StudentService studentService;

  @Override
  public ResponseEntity<List<StudentResponse>> findAll() {
    return ResponseEntity.ok().body(studentService.findAll());
  }

  @Override
  public ResponseEntity<StudentResponse> findById(long id) {
    return ResponseEntity.ok().body(studentService.findById(id));
  }

  @Override
  public ResponseEntity<Void> deleteById(long id) {
    studentService.delete(id);
    return ResponseEntity.noContent().build();
  }
  
}
