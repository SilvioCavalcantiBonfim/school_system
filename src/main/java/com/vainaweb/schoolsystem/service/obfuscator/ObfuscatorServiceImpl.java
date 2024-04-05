package com.vainaweb.schoolsystem.service.obfuscator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
class ObfuscatorServiceImpl implements ObfuscatorService {

  @Override
  public String obfuscateCpf(String cpf) {
    Pattern cpfPattern = Pattern.compile("(\\d{3})\\.(\\d{3})\\.(\\d{3})-(\\d{2})");
    Matcher cpfMatcher = cpfPattern.matcher(cpf);

    return cpfMatcher.replaceAll("$1.***.***-$4");
  }

}
