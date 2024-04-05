package com.vainaweb.schoolsystem.exception;

public class ETagMismatchException extends RuntimeException {
  public ETagMismatchException(){
    super("the received ETag does not match the current ETag");
  }
}
