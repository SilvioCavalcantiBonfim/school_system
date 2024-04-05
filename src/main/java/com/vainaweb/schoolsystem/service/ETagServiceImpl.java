package com.vainaweb.schoolsystem.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.CRC32;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ETagServiceImpl implements ETagService {

  private final CRC32 crc32Checksum;

  @Override
  public long generate(Serializable dataObject) throws IOException {
    ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
    objectOutputStream.writeObject(dataObject);

    crc32Checksum.reset();
    crc32Checksum.update(byteOutputStream.toByteArray());

    return crc32Checksum.getValue();
  }
  
}
