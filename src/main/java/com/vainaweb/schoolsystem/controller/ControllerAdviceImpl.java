package com.vainaweb.schoolsystem.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.vainaweb.schoolsystem.exception.CollaboratorNotFoundException;
import jakarta.validation.ConstraintViolationException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdviceImpl implements ControllerAdvice {

  @Override
  public ResponseEntity<ErrorResponse> handleCollaboratorNotFound(CollaboratorNotFoundException ex) {
    return ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
    StringBuilder message = new StringBuilder();
    message.append("The parameter '");
    String parameterName = ex.getPropertyName();
    message.append(parameterName);
    message.append("' is missing.");
    return ResponseEntity.badRequest()
        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message.toString(), LocalDateTime.now()));
  }

  @Override
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    StringBuilder message = createMenssage(e);
    return ResponseEntity.badRequest()
        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message.toString(), LocalDateTime.now()));
  }

  private final StringBuilder createMenssage(MethodArgumentNotValidException e) {
    StringBuilder message = new StringBuilder();
    String fildsList = e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
        .collect(Collectors.joining(", "));
    message.append(fildsList);
    message.setCharAt(0, Character.toUpperCase(message.charAt(0)));
    message.append('.');
    return message;
  }

  @Override
  public ResponseEntity<MultErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, String> errorMessage = new HashMap<>();

    ex.getConstraintViolations().forEach(constraintViolation -> {
      String path = constraintViolation.getPropertyPath().toString();
      String[] keys = path.split("\\.");
      String key = keys.length == 0 ? path : keys[keys.length - 1];
      errorMessage.put(key, constraintViolation.getMessage());
    });

    return ResponseEntity.badRequest()
        .body(new MultErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage, LocalDateTime.now()));
  }

  @Override
  public ResponseEntity<ErrorResponse> handleIllegalRole(RuntimeException ex) {
    return ResponseEntity.badRequest()
        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now()));
  }

  @Override
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    String message = ex.getMessage().contains("Invalid ") ? ex.getMessage()
        : "the HTTP request contains malformed syntax or cannot be understood by the server.";
    return ResponseEntity.badRequest()
        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, LocalDateTime.now()));
  }

}
