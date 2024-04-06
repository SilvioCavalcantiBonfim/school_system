package com.vainaweb.schoolsystem.model;

import com.vainaweb.schoolsystem.exception.IllegalCourseException;

public enum Course {
  FRONTEND, 
  BACKEND, 
  MOBILE, 
  DATASCIENCE, 
  ECOMMERCE, 
  CLOUD;

  public static Course of(String course){
    try {
      return Course.valueOf(course.toUpperCase());
    } catch (Exception e) {
      throw new IllegalCourseException();
    }
  }
}
