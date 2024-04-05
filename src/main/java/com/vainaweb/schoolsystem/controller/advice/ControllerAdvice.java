package com.vainaweb.schoolsystem.controller.advice;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.vainaweb.schoolsystem.exception.AlreadyCpfException;
import com.vainaweb.schoolsystem.exception.AlreadyEmailException;
import com.vainaweb.schoolsystem.exception.CollaboratorNotFoundException;
import com.vainaweb.schoolsystem.exception.ETagMismatchException;
import com.vainaweb.schoolsystem.exception.IllegalRoleException;
import com.vainaweb.schoolsystem.exception.IllegalStateStringException;

import jakarta.validation.ConstraintViolationException;

public interface ControllerAdvice {

  @ExceptionHandler(CollaboratorNotFoundException.class)
  ResponseEntity<ErrorResponse> handleCollaboratorNotFound(CollaboratorNotFoundException ex);

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex);

  @ExceptionHandler(ConstraintViolationException.class)
  ResponseEntity<MultErrorResponse> handleConstraintViolation(ConstraintViolationException ex);

  @ExceptionHandler({IllegalRoleException.class, IllegalStateStringException.class})
  ResponseEntity<ErrorResponse> handleIllegalRole(RuntimeException ex);

  @ExceptionHandler(HttpMessageNotReadableException.class)
  ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex);

  @ExceptionHandler({AlreadyEmailException.class, AlreadyCpfException.class, ETagMismatchException.class})
  ResponseEntity<ErrorResponse> handleAlready(RuntimeException ex);

  public record ErrorResponse(int status, String message, LocalDateTime timestamp) {
  }

  public record MultErrorResponse(int status, Map<String, String> message, LocalDateTime timestamp) {
  }

}
