package com.counter.mappers;

import com.counter.entity.Code;
import com.counter.payload.CodeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CodeMapper {

  CodeDto toDto(Code code);

  Code toEntity(CodeDto codeDto);

}
