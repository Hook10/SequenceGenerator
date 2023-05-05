package com.counter.controller;

import com.counter.payload.CodeDto;
import com.counter.service.CodeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/codes")
@RequiredArgsConstructor
public class CodeController {

  private final CodeServiceImpl codeService;

  @GetMapping("/generate")
  public ResponseEntity<CodeDto> getNextCode() {
    return ResponseEntity.ok(codeService.generateNextCode());
  }

}
