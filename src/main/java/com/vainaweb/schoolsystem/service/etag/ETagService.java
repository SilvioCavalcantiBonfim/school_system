package com.vainaweb.schoolsystem.service.etag;

import java.io.IOException;
import java.io.Serializable;

public interface ETagService {
  String generate(Serializable data) throws IOException;
  void validate(String etag, Serializable data);
}
