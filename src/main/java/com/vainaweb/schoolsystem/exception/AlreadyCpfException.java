package com.vainaweb.schoolsystem.exception;

public class AlreadyCpfException extends RuntimeException {
  public AlreadyCpfException(){
    super("cpf already registered");
  }
}
