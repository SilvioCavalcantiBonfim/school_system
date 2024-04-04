package com.vainaweb.schoolsystem.configuration;

import java.util.zip.CRC32;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CRC32Config {

  @Bean
  public CRC32 crc32(){
    return new CRC32();
  }
}
