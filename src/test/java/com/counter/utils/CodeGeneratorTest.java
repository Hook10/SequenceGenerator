package com.counter.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CodeGeneratorTest {

  @Test
  void testGenerateCode() {
    CodeGenerator generator = new CodeGenerator();

    // Test null input
    Assertions.assertNull(generator.generateCode(null));

    // Test invalid starting code
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      generator.generateCode("0a");
    });
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      generator.generateCode("a");
    });
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      generator.generateCode("b0");
    });
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      generator.generateCode("a0");
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      generator.generateCode("a0c");
    });
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      generator.generateCode("111");
    });
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      generator.generateCode("zzz");
    });

    // Test incrementing a code that ends with a digit
    Assertions.assertEquals("a0a1", generator.generateCode("a0a0"));
    Assertions.assertEquals("a0a1a0", generator.generateCode("a0a0z9"));
    Assertions.assertEquals("a1b2", generator.generateCode("a1b1"));
    Assertions.assertEquals("b0b1", generator.generateCode("b0b0"));
    Assertions.assertEquals("b4b5", generator.generateCode("b4b4"));
    Assertions.assertEquals("a0a0a0", generator.generateCode("z9z9"));
    Assertions.assertEquals("a0a0a0a0", generator.generateCode("z9z9z9"));
  }

}
