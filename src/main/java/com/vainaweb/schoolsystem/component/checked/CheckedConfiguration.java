package com.vainaweb.schoolsystem.component.checked;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vainaweb.schoolsystem.repository.CollaboratorRepository;
import com.vainaweb.schoolsystem.repository.StudentRepository;

@Configuration
public class CheckedConfiguration {

  @Autowired
  private CollaboratorRepository collaboratorRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Bean
  public Checked studentChecked() {
    return new CheckedImpl(studentRepository);
  }

  @Bean
  public Checked collaboratorChecked() {
    return new CheckedImpl(collaboratorRepository);
  }
}
