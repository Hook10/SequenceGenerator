package com.counter.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class CodeGenerator {

  private static final String CODE_PATTERN = "^([a-z][0-9])+$";
  public static final char MAX_NUM = '9';
  public static final char MIN_NUM = '0';
  public static final char MAX_LETTER = 'z';
  public static final char MIN_LETTER = 'a';
  public static final String INCREMENT = "a0";

  public synchronized String generateCode(String str) {
    var leftToIncrement = false;
    boolean incremented = false;
    if (str == null) {
      return null;
    }
    if (!isValidCode(str) || str.length() < 4) {
      throw new IllegalArgumentException("Invalid starting code: " + str);
    }
    char[] charArray = str.toCharArray();
    int i = charArray.length - 1;

    while (!incremented && i >= 0) {
      if (Character.isDigit(charArray[i])) {
        if (leftToIncrement  && charArray[i] != MAX_NUM) {
          charArray[i]++;
          leftToIncrement=false;
          incremented = true;
        } else if (charArray[i] < MAX_NUM && charArray[i] != MIN_NUM) {
          charArray[i]++;
          incremented = true;
        } else if (charArray[i] == MAX_NUM) {
          charArray[i] = MIN_NUM;
          leftToIncrement=true;
        } else if (!leftToIncrement && charArray[i] == MIN_NUM) {
          charArray[i]++;
          incremented = true;
        }
      }

      if (Character.isLetter(charArray[i]) && leftToIncrement) {
          if (charArray[i] != MAX_LETTER) {
            charArray[i]++;
            leftToIncrement = false;
            incremented = true;
          } else {
            charArray[i] = MIN_LETTER;
            leftToIncrement = true;
          }
      }
      i--;
    }

    if (leftToIncrement   && (charArray[0] == MIN_LETTER && charArray[1] == MIN_NUM)) {
      return INCREMENT + new String(charArray);
    }

    return new String(charArray);
  }

  public boolean isValidCode(String code) {
    Pattern pattern = Pattern.compile(CODE_PATTERN);
    Matcher matcher = pattern.matcher(code);
    return matcher.matches();
  }
}
