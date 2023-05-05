package com.counter.utils;

import com.counter.entity.Code;
import java.util.Comparator;

public class LetterNumberComparator implements Comparator<Code> {

  @Override
  public int compare(Code code1, Code code2) {
    String s1 = code1.getValue();
    String s2 = code2.getValue();
    String letter1 = s1.replaceAll("[0-9]", "");
    String letter2 = s2.replaceAll("[0-9]", "");

    int result = letter1.compareTo(letter2);
    if (result != 0) {
      return result;
    }

    String number1 = s1.replaceAll("[a-zA-Z]", "");
    String number2 = s2.replaceAll("[a-zA-Z]", "");

    return Integer.compare(Integer.parseInt(number1), Integer.parseInt(number2));
  }

}
