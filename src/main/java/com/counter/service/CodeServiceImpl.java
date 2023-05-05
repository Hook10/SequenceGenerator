package com.counter.service;

import com.counter.entity.Code;
import com.counter.mappers.CodeMapper;
import com.counter.payload.CodeDto;
import com.counter.repository.CodeRepository;
import com.counter.utils.CodeGenerator;
import com.counter.utils.LetterNumberComparator;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

  public static final String STARTING_CODE = "a0a0";

  private final CodeRepository codeRepository;
  private final CodeGenerator codeGenerator;
  private final CodeMapper codeMapper;

  @Override
  public CodeDto generateNextCode() {
    log.info("Code generating started ... ");
    List<Code> codes = new ArrayList<>(codeRepository.findAll());

    Code codeMaxFromDb = codes.stream().max(new LetterNumberComparator()).orElse(null);

    Code savedCode;
    if (codeMaxFromDb == null) {
      savedCode = codeRepository.save(Code.builder()
          .value(STARTING_CODE).build());
      log.info("Generated code: {}", savedCode.getValue());
      return codeMapper.toDto(savedCode);
    }

    String newCode = codeGenerator.generateCode(codeMaxFromDb.getValue());
    savedCode = codeRepository.save(Code.builder()
        .value(newCode).build());
    log.info("Generated code: {}", savedCode.getValue());
    codeRepository.deleteById(codeMaxFromDb.getId());

    return codeMapper.toDto(savedCode);
  }


}