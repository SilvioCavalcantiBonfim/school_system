package com.vainaweb.schoolsystem.exception;

public class IllegalStateStringException extends RuntimeException {
  public IllegalStateStringException(){
    super("the provided state is not valid.");
  }
}
