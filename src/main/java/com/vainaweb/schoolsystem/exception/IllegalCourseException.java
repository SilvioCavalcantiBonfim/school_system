package com.vainaweb.schoolsystem.exception;

public class IllegalCourseException extends RuntimeException {
  public IllegalCourseException() {
    super("the provided course is not valid.");
  }
}
