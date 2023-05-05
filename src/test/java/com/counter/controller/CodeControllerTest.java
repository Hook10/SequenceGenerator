package com.counter.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.counter.payload.CodeDto;
import com.counter.service.CodeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = CodeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CodeControllerTest {

  @MockBean
  private CodeServiceImpl codeService;

  @Autowired
  private MockMvc mockMvc;

  private CodeDto codeDto;

  @BeforeEach
  public void init() {
    codeDto = CodeDto.builder().id(1L).value("a0a1").build();
  }

  @Test
  void codeGeneratingTest() throws Exception {
    given(codeService.generateNextCode()).willReturn(codeDto);
    ResultActions response = mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/codes/generate")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(codeDto.getId()))
        .andExpect(jsonPath("$.value").value(codeDto.getValue()))
        .andDo(print());
  }

}
