package com.vainaweb.schoolsystem.component.checked;

public interface Checked {
  void checkCpfAlreadyExists(final String cpf);
  void checkEmailAlreadyExists(final String email);
  void checkEmailOrCpfAlreadyExists(final String email, final String cpf);
}
