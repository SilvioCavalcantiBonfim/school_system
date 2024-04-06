package com.vainaweb.schoolsystem.model;

import com.vainaweb.schoolsystem.exception.IllegalRoleException;

public enum Role  {
  INSTRUTOR, 
  FACILITADOR, 
  COORDENACAO, 
  ADMINSTRATIVO;

  public static Role of(String role){
    try {
      return Role.valueOf(role.toUpperCase());
    } catch (Exception e) {
      throw new IllegalRoleException();
    }
  }
}
