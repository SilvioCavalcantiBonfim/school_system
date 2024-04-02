package com.vainaweb.schoolsystem.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.vainaweb.schoolsystem.exception.CollaboratorNotFoundException;

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

}
