package com.nttdata.services.trivia.mappers;


import com.nttdata.services.trivia.dto.QuestionDto;
import com.nttdata.services.trivia.model.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

  QuestionDto toDto(Question question);

  Question toModel(QuestionDto questionDto);

  List<QuestionDto> toDtoList(List<Question> questionList);

  List<Question> toModelList(List<QuestionDto> questionDtoList);
}
