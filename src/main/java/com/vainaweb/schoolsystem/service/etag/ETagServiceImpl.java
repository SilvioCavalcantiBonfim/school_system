package com.vainaweb.schoolsystem.service.etag;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;
import java.util.zip.CRC32;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vainaweb.schoolsystem.exception.ETagMismatchException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ETagServiceImpl implements ETagService {

  private final CRC32 crc32Checksum;
  private static final Logger LOGGER = LoggerFactory.getLogger(ETagServiceImpl.class);

  @Override
  public String generate(Serializable dataObject) throws IOException {
    ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
    objectOutputStream.writeObject(dataObject);

    crc32Checksum.reset();
    crc32Checksum.update(byteOutputStream.toByteArray());

    return String.format("%x", crc32Checksum.getValue());
  }

  @Override
  public void validate(String etag, Serializable data) {
    Optional.ofNullable(etag).ifPresent(tag -> {
      try {
        String dataEtag = generate(data);
        LOGGER.debug(dataEtag);
        if (!dataEtag.equals(tag)) {
          throw new ETagMismatchException();
        }
      } catch (IOException e) {
      }
    });
  }

}
