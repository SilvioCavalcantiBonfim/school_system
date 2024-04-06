package com.vainaweb.schoolsystem.service.student;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.stereotype.Service;

import com.vainaweb.schoolsystem.component.checked.Checked;
import com.vainaweb.schoolsystem.component.mapper.StudentMapper;
import com.vainaweb.schoolsystem.dto.request.StudentRequest;
import com.vainaweb.schoolsystem.dto.request.StudentUpdateRequest;
import com.vainaweb.schoolsystem.dto.response.StudentResponse;
import com.vainaweb.schoolsystem.exception.StudentNotFoundException;
import com.vainaweb.schoolsystem.model.Student;
import com.vainaweb.schoolsystem.repository.StudentRepository;
import com.vainaweb.schoolsystem.service.etag.ETagService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
  
  private final StudentRepository studantRepository;
  private final StudentMapper studentMapper;
  private final ETagService eTagService;
  private final Checked studentChecked;
  
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

  @Override
  public URI create(StudentRequest request) throws URISyntaxException {
    studentChecked.checkCpfAlreadyExists(request.cpf());
    studentChecked.checkEmailAlreadyExists(request.email());
    Student student = studentMapper.toEntity(request);
    student = saveStudent(student);
    URI location = new URI(String.format("/estudantes/%d", student.getId()));
    return location;
  }

  private Student saveStudent(@Valid Student student) {
    return studantRepository.save(student);
  }

  @Override
  public String update(long id, StudentUpdateRequest request, String ifMatch) throws IOException {
    Student student = findStudentById(id);
    eTagService.validate(ifMatch, student);
    student = saveStudent(mergeStudentData(request, student));
    return eTagService.generate(student);
  }

  private Student mergeStudentData(StudentUpdateRequest request, Student student) {
    return studentMapper.merge(student, request);
  }

  private Student findStudentById(long id) {
    return studantRepository.findById(id).orElseThrow(() -> new StudentNotFoundException());
  }
}
