package com.vainaweb.schoolsystem.service;

import java.io.IOException;
import java.io.Serializable;

public interface ETagService {
  long generate(Serializable data) throws IOException;
}
