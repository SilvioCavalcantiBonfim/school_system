package com.vainaweb.schoolsystem.exception;

public class IllegalRoleException extends RuntimeException {
  public IllegalRoleException() {
    super("the provided role is not valid.");
  }
}
