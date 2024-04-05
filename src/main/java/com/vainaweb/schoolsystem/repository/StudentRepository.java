package com.vainaweb.schoolsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vainaweb.schoolsystem.model.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {}
