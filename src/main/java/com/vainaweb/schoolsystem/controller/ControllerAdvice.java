package com.vainaweb.schoolsystem.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.vainaweb.schoolsystem.exception.CollaboratorNotFoundException;

public interface ControllerAdvice {

  @ExceptionHandler(CollaboratorNotFoundException.class)
  ResponseEntity<ErrorResponse> handleCollaboratorNotFound(CollaboratorNotFoundException ex);

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex);

  public record ErrorResponse(int status, String message, LocalDateTime timestamp) {
  }

}
