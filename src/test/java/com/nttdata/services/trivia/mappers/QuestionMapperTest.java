package com.nttdata.services.trivia.mappers;

import static org.junit.jupiter.api.Assertions.*;

import com.nttdata.services.trivia.dto.QuestionDto;
import com.nttdata.services.trivia.model.Question;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class QuestionMapperTest {

  private final QuestionMapper mapper = Mappers.getMapper(QuestionMapper.class);

  @Test
  void toDto_shouldMapModelToDto_whenValidModelProvided() {
    Question model = Question.builder()
        .id("abc123")
        .questionId(1)
        .topic("Science")
        .text("What is H2O?")
        .options(Arrays.asList("Water", "Oxygen", "Hydrogen"))
        .answer("Water")
        .build();

    QuestionDto dto = mapper.toDto(model);

    assertNotNull(dto);
    assertEquals(model.getQuestionId(), dto.getQuestionId());
    assertEquals(model.getTopic(), dto.getTopic());
    assertEquals(model.getText(), dto.getText());
    assertEquals(model.getOptions(), dto.getOptions());
    assertEquals(model.getAnswer(), dto.getAnswer());
  }

  @Test
  void toModel_shouldMapDtoToModel_whenValidDtoProvided() {
    QuestionDto dto = QuestionDto.builder()
        .questionId(2)
        .topic("Math")
        .text("What is 2+2?")
        .options(Arrays.asList("3", "4", "5"))
        .answer("4")
        .build();

    Question model = mapper.toModel(dto);

    assertNotNull(model);
    assertEquals(dto.getQuestionId(), model.getQuestionId());
    assertEquals(dto.getTopic(), model.getTopic());
    assertEquals(dto.getText(), model.getText());
    assertEquals(dto.getOptions(), model.getOptions());
    assertEquals(dto.getAnswer(), model.getAnswer());
  }

  @Test
  void toDtoList_shouldMapModelListToDtoList_whenValidListProvided() {
    List<Question> models = List.of(
        Question.builder().questionId(1).topic("A").text("Q1").options(List.of("1")).answer("1").build(),
        Question.builder().questionId(2).topic("B").text("Q2").options(List.of("2")).answer("2").build()
    );

    List<QuestionDto> dtos = mapper.toDtoList(models);

    assertNotNull(dtos);
    assertEquals(2, dtos.size());
    assertEquals("Q1", dtos.get(0).getText());
    assertEquals("Q2", dtos.get(1).getText());
  }

  @Test
  void toModelList_shouldMapDtoListToModelList_whenValidListProvided() {
    List<QuestionDto> dtos = List.of(
        QuestionDto.builder().questionId(1).topic("X").text("QX").options(List.of("X")).answer("X").build(),
        QuestionDto.builder().questionId(2).topic("Y").text("QY").options(List.of("Y")).answer("Y").build()
    );

    List<Question> models = mapper.toModelList(dtos);

    assertNotNull(models);
    assertEquals(2, models.size());
    assertEquals("QX", models.get(0).getText());
    assertEquals("QY", models.get(1).getText());
  }

  @Test
  void toDto_shouldReturnNull_whenInputIsNull() {
    QuestionDto dto = mapper.toDto(null);
    assertNull(dto, "El resultado debe ser null cuando la entrada es null");
  }

  @Test
  void toModel_shouldReturnNull_whenInputIsNull() {
    Question model = mapper.toModel(null);
    assertNull(model, "El resultado debe ser null cuando la entrada es null");
  }

  @Test
  void toDtoList_shouldReturnNull_whenInputListIsNull() {
    List<QuestionDto> dtoList = mapper.toDtoList(null);
    assertNull(dtoList, "La lista resultante debe ser null cuando la entrada es null");
  }

  @Test
  void toModelList_shouldReturnNull_whenInputListIsNull() {
    List<Question> modelList = mapper.toModelList(null);
    assertNull(modelList, "La lista resultante debe ser null cuando la entrada es null");
  }

  @Test
  void toDto_shouldCopyOptions_whenOptionsIsNotNull() {
    List<String> options = Arrays.asList("A", "B", "C");
    Question model = Question.builder()
        .questionId(1)
        .topic("Test")
        .text("Sample?")
        .options(options)
        .answer("A")
        .build();

    QuestionDto dto = mapper.toDto(model);

    assertNotNull(dto.getOptions());
    assertEquals(options, dto.getOptions());
    assertNotSame(options, dto.getOptions(), "Debe ser una copia, no la misma instancia");
  }

  @Test
  void toDto_shouldSetOptionsToNull_whenOptionsIsNull() {
    Question model = Question.builder()
        .questionId(2)
        .topic("Test")
        .text("Sample?")
        .options(null)
        .answer("B")
        .build();

    QuestionDto dto = mapper.toDto(model);

    assertNull(dto.getOptions(), "Options debe ser null si el modelo tiene null");
  }


  @Test
  void toModel_shouldCopyOptions_whenOptionsIsNotNull() {
    List<String> options = Arrays.asList("Option A", "Option B");
    QuestionDto dto = QuestionDto.builder()
        .questionId(1)
        .topic("General")
        .text("Sample question?")
        .options(options)
        .answer("Option A")
        .build();

    Question model = mapper.toModel(dto);

    assertNotNull(model.getOptions());
    assertEquals(options, model.getOptions());
    assertNotSame(options, model.getOptions(), "Debe ser una copia, no la misma instancia");
  }

  @Test
  void toModel_shouldSetOptionsToNull_whenOptionsIsNull() {
    QuestionDto dto = QuestionDto.builder()
        .questionId(2)
        .topic("General")
        .text("Another question?")
        .options(null)
        .answer("Option B")
        .build();

    Question model = mapper.toModel(dto);

    assertNull(model.getOptions(), "Options debe ser null si el DTO tiene null");
  }


}
