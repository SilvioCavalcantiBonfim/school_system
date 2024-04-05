package com.vainaweb.schoolsystem.repository;

public interface AbstractRepository {
  boolean existsByEmail(String email);
  boolean existsByCpf(String cpf);
}
