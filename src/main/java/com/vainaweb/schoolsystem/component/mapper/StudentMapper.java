package com.vainaweb.schoolsystem.component.mapper;

import java.util.Objects;
import org.springframework.stereotype.Component;

import com.vainaweb.schoolsystem.dto.request.StudentRequest;
import com.vainaweb.schoolsystem.dto.response.StudentResponse;
import com.vainaweb.schoolsystem.exception.IllegalCourseException;
import com.vainaweb.schoolsystem.model.Course;
import com.vainaweb.schoolsystem.model.Student;
import com.vainaweb.schoolsystem.model.Student.StudentBuilder;
import com.vainaweb.schoolsystem.service.obfuscator.ObfuscatorService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudentMapper implements Mapper<Student, StudentResponse, StudentRequest> {

  private final AddressMapper addressMapper;
  private final ObfuscatorService obfuscatorService;

  @Override
  public StudentResponse toResponse(Student student) {
    return StudentResponse.builder()
        .id(student.getId())
        .name(student.getName())
        .email(student.getEmail())
        .cpf(obfuscatorService.obfuscateCpf(student.getCpf()))
        .phone(student.getPhone())
        .course(student.getCourse().toString())
        .address(addressMapper.toResponse(student.getAddress()))
        .build();
  }

  @Override
  public Student toEntity(StudentRequest request) {

    StudentBuilder student = Student.builder()
        .name(request.name())
        .cpf(request.cpf())
        .email(request.email())
        .phone(request.phone())
        .address(addressMapper.toEntity(request.address()));
    if (Objects.nonNull(request.course())) {
      try {
        student.course(Course.valueOf(request.course().toUpperCase()));
      } catch (Exception e) {
        throw new IllegalCourseException();
      }
    }
    return student.build();
  }
}
