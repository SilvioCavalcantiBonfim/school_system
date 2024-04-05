package com.vainaweb.schoolsystem.service.student;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.vainaweb.schoolsystem.dto.request.StudentRequest;
import com.vainaweb.schoolsystem.dto.response.StudentResponse;


public interface StudentService {
  List<StudentResponse> findAll();
  StudentResponse findById(long id);
  void delete(long id);
  URI create(StudentRequest request) throws URISyntaxException;
}