package com.vainaweb.schoolsystem.component.checked;

import java.util.Optional;

import com.vainaweb.schoolsystem.exception.AlreadyCpfException;
import com.vainaweb.schoolsystem.exception.AlreadyEmailException;
import com.vainaweb.schoolsystem.repository.AbstractRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CheckedImpl implements Checked {

  private final AbstractRepository repository;

  @Override
  public void checkCpfAlreadyExists(String cpf) {
    Optional.ofNullable(cpf).ifPresent(e -> {
      if (repository.existsByCpf(cpf)) {
        throw new AlreadyCpfException();
      }
    });
  }

  @Override
  public void checkEmailAlreadyExists(String email) {
    Optional.ofNullable(email).ifPresent(e -> {
      if (repository.existsByEmail(email)) {
        throw new AlreadyEmailException();
      }
    });
  }

  @Override
  public void checkEmailOrCpfAlreadyExists(String email, String cpf) {
    checkCpfAlreadyExists(cpf);
    checkEmailAlreadyExists(email);
  }

}
