package com.vainaweb.schoolsystem.service;

import java.util.List;

import com.vainaweb.schoolsystem.dto.response.StudentResponse;


public interface StudentService {
  List<StudentResponse> findAll();
  StudentResponse findById(long id);
  void delete(long id);
}
