package com.vainaweb.schoolsystem.component.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.vainaweb.schoolsystem.dto.request.StudentRequest;
import com.vainaweb.schoolsystem.dto.request.StudentUpdateRequest;
import com.vainaweb.schoolsystem.dto.response.StudentResponse;
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

    Optional.ofNullable(request.course()).map(Course::of).ifPresent(student::course);
    
    return student.build();
  }

  public Student merge(Student entity, StudentUpdateRequest request) {
    Optional.ofNullable(request.name()).ifPresent(entity::setName);
    Optional.ofNullable(request.phone()).ifPresent(entity::setPhone);
    Optional.ofNullable(request.course()).map(Course::of).ifPresent(entity::setCourse);
    Optional.ofNullable(request.address())
        .map(addr -> addressMapper.merge(entity.getAddress(), addr))
        .ifPresent(entity::setAddress);

    return entity;
  }
}
