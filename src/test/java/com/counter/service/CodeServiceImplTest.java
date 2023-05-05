package com.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.counter.entity.Code;
import com.counter.mappers.CodeMapper;
import com.counter.payload.CodeDto;
import com.counter.repository.CodeRepository;
import com.counter.utils.CodeGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CodeServiceImplTest {

  private CodeServiceImpl codeService;
  @Mock
  private CodeRepository codeRepository;
  @Mock
  private CodeGenerator codeGenerator;
  @Mock
  private CodeMapper codeMapper;

  @BeforeEach
  void setUp() {
    codeService = new CodeServiceImpl(codeRepository, codeGenerator, codeMapper);
  }

  @Test
  @DisplayName("Test generating next code")
  void testGenerateNextCode() {
    // Arrange
    List<Code> codes = new ArrayList<>();
    Code maxCode = Code.builder().id(1L).value("a9a9").build();
    codes.add(maxCode);
    when(codeRepository.findAll()).thenReturn(codes);
    when(codeRepository.save(any(Code.class))).thenReturn(maxCode);
    when(codeMapper.toDto(maxCode)).thenReturn(
        CodeDto.builder().id(maxCode.getId()).value(maxCode.getValue()).build());
    when(codeGenerator.generateCode(maxCode.getValue())).thenReturn("b0b0");

    CodeDto codeDto = codeService.generateNextCode();

    assertNotNull(codeDto);
    assertEquals(maxCode.getValue(), codeDto.getValue());
    verify(codeRepository, times(1)).findAll();
    verify(codeRepository, times(1)).save(any(Code.class));
    verify(codeRepository, times(1)).deleteById(maxCode.getId());
    verify(codeMapper, times(1)).toDto(maxCode);
    verify(codeGenerator, times(1)).generateCode(maxCode.getValue());
  }

  @Test
  @DisplayName("Test generating next code with empty repository")
  void testGenerateNextCodeEmpty() {
    when(codeRepository.findAll()).thenReturn(Collections.emptyList());
    when(codeRepository.save(any(Code.class))).thenReturn(
        Code.builder().id(1L).value(CodeServiceImpl.STARTING_CODE).build());
    when(codeMapper.toDto(any(Code.class))).thenReturn(
        CodeDto.builder().id(1L).value(CodeServiceImpl.STARTING_CODE).build());

    CodeDto codeDto = codeService.generateNextCode();

    assertNotNull(codeDto);
    assertEquals(CodeServiceImpl.STARTING_CODE, codeDto.getValue());
    verify(codeRepository, times(1)).findAll();
    verify(codeRepository, times(1)).save(any(Code.class));
    verify(codeMapper, times(1)).toDto(any(Code.class));
  }
}

