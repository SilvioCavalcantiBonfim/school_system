package com.vainaweb.schoolsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vainaweb.schoolsystem.dto.response.StudentResponse;
import com.vainaweb.schoolsystem.exception.StudentNotFoundException;
import com.vainaweb.schoolsystem.model.mapper.StudentMapper;
import com.vainaweb.schoolsystem.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
  
  private final StudentRepository studantRepository;
  private final StudentMapper studentMapper;
  
  @Override
  public List<StudentResponse> findAll() {
    return studantRepository.findAll().stream().map(studentMapper::toResponse).toList();
  }

  @Override
  public StudentResponse findById(long id) {
    return studantRepository.findById(id).map(studentMapper::toResponse).orElseThrow(() -> new StudentNotFoundException());
  }

  @Override
  public void delete(long id) {
    studantRepository.findById(id).orElseThrow(() -> new StudentNotFoundException());
    studantRepository.deleteById(id);
  }
  
}
