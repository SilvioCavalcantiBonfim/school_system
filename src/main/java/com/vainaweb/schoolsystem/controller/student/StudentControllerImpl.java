package com.vainaweb.schoolsystem.controller.student;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vainaweb.schoolsystem.dto.request.StudentRequest;
import com.vainaweb.schoolsystem.dto.response.StudentResponse;
import com.vainaweb.schoolsystem.service.student.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("estudantes")
@RequiredArgsConstructor
class StudentControllerImpl implements StudentController {

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

  @Override
  public ResponseEntity<Void> create(StudentRequest request) throws URISyntaxException {
    URI location = studentService.create(request);
    return ResponseEntity.created(location).build();
  }
  
}
