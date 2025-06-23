package com.nttdata.services.trivia.mappers;


import com.nttdata.services.trivia.dto.QuestionDto;
import com.nttdata.services.trivia.model.Question;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Question mapper interface.
 *
 * @author <a href="ajavierv@emeal.nttdata.com">ajavierv@emeal.nttdata.com</a>
 */
@Mapper(componentModel = "spring")
public interface QuestionMapper {

  /**
   * convert from question model to question dto.
   *
   * @param question a question model.
   * @return a question dto.
   */
  QuestionDto toDto(Question question);

  /**
   * convert from question dto to question model.
   *
   * @param questionDto a question dto.
   * @return a question model.
   */
  Question toModel(QuestionDto questionDto);

  /**
   * converts from question model list to question dto list.
   *
   * @param questionList a question model list.
   * @return a question model dto list.
   */
  List<QuestionDto> toDtoList(List<Question> questionList);

  /**
   * converts from question dto list to question model list.
   *
   * @param questionDtoList a question dto list.
   * @return a question model list.
   */
  List<Question> toModelList(List<QuestionDto> questionDtoList);
}
