package com.vainaweb.schoolsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vainaweb.schoolsystem.model.Student;

public interface StudentRepository extends AbstractRepository, JpaRepository<Student, Long> {
}
