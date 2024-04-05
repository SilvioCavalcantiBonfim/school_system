package com.vainaweb.schoolsystem.exception;

public class AlreadyEmailException extends RuntimeException {
  public AlreadyEmailException(){
    super("email address already registered");
  }
}
