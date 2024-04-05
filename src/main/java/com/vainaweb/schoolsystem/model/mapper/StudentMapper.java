package com.vainaweb.schoolsystem.model.mapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.vainaweb.schoolsystem.dto.request.StudentRequest;
import com.vainaweb.schoolsystem.dto.response.StudentResponse;
import com.vainaweb.schoolsystem.model.entity.Student;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StudentMapper implements Mapper<Student, StudentResponse, StudentRequest> {

  private final AddressMapper addressMapper;

  @Override
  public StudentResponse toResponse(Student student) {
    return new StudentResponse(student.getId(), student.getName(), student.getEmail(),
        cpfObfuscate(student.getCpf()),
        student.getPhone(),
        student.getCourse().toString(), 
        addressMapper.toResponse(student.getAddress()));
  }

  private String cpfObfuscate(String cpf) {
    Pattern pattern = Pattern.compile("(\\d{3})\\.(\\d{3})\\.(\\d{3})-(\\d{2})");
    Matcher matcher = pattern.matcher(cpf);

    return matcher.replaceAll("$1.***.***-$4");
  }

  @Override
  public Student toEntity(StudentRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
  }
}
