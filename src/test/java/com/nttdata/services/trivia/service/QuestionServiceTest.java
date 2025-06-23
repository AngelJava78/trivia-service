package com.nttdata.services.trivia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nttdata.services.trivia.dto.CategoryCountDto;
import com.nttdata.services.trivia.dto.QuestionDto;
import com.nttdata.services.trivia.mappers.QuestionMapper;
import com.nttdata.services.trivia.model.Question;
import com.nttdata.services.trivia.repository.QuestionRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

  @Mock
  private QuestionRepository questionRepository;

  @Mock
  private QuestionMapper questionMapper;

  @InjectMocks
  private QuestionService questionService;

  private Question createSampleQuestion() {
    return Question.builder()
        .id("1")
        .questionId(101)
        .topic("Food")
        .text("¿Cuál es el ingrediente principal del guacamole?")
        .options(List.of("Tomate", "Aguacate", "Cebolla", "Chile"))
        .answer("Aguacate")
        .build();
  }

  private QuestionDto createSampleQuestionDto() {
    return QuestionDto.builder()
        .questionId(101)
        .topic("Food")
        .text("¿Cuál es el ingrediente principal del guacamole?")
        .options(Arrays.asList("Tomate", "Aguacate", "Cebolla", "Chile"))
        .answer("Aguacate")
        .build();
  }

  @Test
  void getAllQuestions_shouldReturnListOfQuestionDtos_whenRepositoryReturnsQuestions() {
    // Arrange
    Question question = createSampleQuestion();

    QuestionDto questionDto = createSampleQuestionDto();

    when(questionRepository.findAll()).thenReturn(List.of(question));
    when(questionMapper.toDto(question)).thenReturn(questionDto);

    // Act
    ResponseEntity<List<QuestionDto>> response = questionService.getAllQuestions();

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, response.getBody().size());
    assertEquals(questionDto, response.getBody().get(0));
    verify(questionRepository, times(1)).findAll();
    verify(questionMapper, times(1)).toDto(question);
  }

  @Test
  void getQuestionsByCategory_shouldReturnListOfQuestionDtos_whenCategoryMatches() {

    // Arrange
    Question question = createSampleQuestion();

    QuestionDto questionDto = createSampleQuestionDto();

    String category = "Food";

    when(questionRepository.findAll()).thenReturn(List.of(question));
    when(questionMapper.toDto(question)).thenReturn(questionDto);

    // Act
    ResponseEntity<List<QuestionDto>> response = questionService.getQuestionsByCategory(category);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, response.getBody().size());
    assertEquals(questionDto, response.getBody().get(0));
    assertEquals(category, response.getBody().get(0).getTopic());
    verify(questionRepository, times(1)).findAll();
    verify(questionMapper, times(1)).toDto(question);
  }

  @Test
  void getQuestionByCategoryAndId_shouldReturnQuestionDto_whenMatchFound() {
    // Arrange
    String category = "Food";
    long id = 101L;

    Question question = createSampleQuestion();

    QuestionDto expectedDto = createSampleQuestionDto();

    when(questionRepository.findAll()).thenReturn(List.of(question));
    when(questionMapper.toDto(question)).thenReturn(expectedDto);

    // Act
    ResponseEntity<QuestionDto> response = questionService.getQuestionByCategoryAndId(category, id);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(expectedDto, response.getBody());
    verify(questionRepository).findAll();
    verify(questionMapper).toDto(question);
  }

  @Test
  void getQuestionByCategoryAndId_shouldReturnNotFound_whenNoMatch() {
    // Arrange
    String category = "Math";
    long id = 99L;

    Question question = createSampleQuestion();

    when(questionRepository.findAll()).thenReturn(List.of(question));

    // Act
    ResponseEntity<QuestionDto> response = questionService.getQuestionByCategoryAndId(category, id);

    // Assert
    assertEquals(404, response.getStatusCodeValue());
    assertNull(response.getBody());
    verify(questionRepository).findAll();
    verify(questionMapper, never()).toDto(any());
  }

  @Test
  void saveQuestion_shouldReturnSavedQuestionDto() {
    // Arrange
    QuestionDto inputDto = createSampleQuestionDto();

    Question questionModel = createSampleQuestion();

    Question savedQuestion = questionModel; // Simulamos que se guarda igual

    QuestionDto expectedDto = inputDto;

    when(questionMapper.toModel(inputDto)).thenReturn(questionModel);
    when(questionRepository.save(questionModel)).thenReturn(savedQuestion);
    when(questionMapper.toDto(savedQuestion)).thenReturn(expectedDto);

    // Act
    ResponseEntity<QuestionDto> response = questionService.saveQuestion(inputDto);

    // Assert
    assertEquals(expectedDto, response.getBody());
    verify(questionMapper).toModel(inputDto);
    verify(questionRepository).save(questionModel);
    verify(questionMapper).toDto(savedQuestion);
  }

  @Test
  void getCategories_shouldReturnGroupedTopicsWithCounts() {
    // Arrange
    Question q1 = Question.builder()
        .id("1")
        .questionId(1)
        .topic("Food")
        .options(List.of("Tomate", "Aguacate", "Cebolla", "Chile"))
        .answer("Aguacate")
        .build();

    Question q2 = Question.builder()
        .id("2")
        .questionId(2)
        .topic("Food")
        .text("¿Qué fruta es amarilla y curva?")
        .options(List.of("Manzana", "Banana", "Pera", "Melón"))
        .answer("Banana")
        .build();

    Question q3 = Question.builder()
        .id("3")
        .questionId(3)
        .topic("Science")
        .text("¿Cuál es el planeta más cercano al sol?")
        .options(List.of("Venus", "Marte", "Mercurio", "Júpiter"))
        .answer("Mercurio")
        .build();

    when(questionRepository.findAll()).thenReturn(List.of(q1, q2, q3));

    // Act
    ResponseEntity<List<CategoryCountDto>> response = questionService.getCategories();

    // Assert
    List<CategoryCountDto> result = response.getBody();
    assertEquals(2, result.size());

    CategoryCountDto foodCategory = result.stream()
        .filter(c -> c.getTopic().equals("food"))
        .findFirst()
        .orElseThrow();

    CategoryCountDto scienceCategory = result.stream()
        .filter(c -> c.getTopic().equals("science"))
        .findFirst()
        .orElseThrow();

    assertEquals(2L, foodCategory.getTotal());
    assertEquals(1L, scienceCategory.getTotal());

    verify(questionRepository, times(1)).findAll();
  }

  @Test
  void getQuestionByCategoryAndId_shouldReturnDto_whenCategoryAndIdMatch() {
    Question question = Question.builder().questionId(1).topic("Science").build();
    QuestionDto dto = QuestionDto.builder().questionId(1).topic("Science").build();

    when(questionRepository.findAll()).thenReturn(List.of(question));
    when(questionMapper.toDto(question)).thenReturn(dto);

    ResponseEntity<QuestionDto> response = questionService.getQuestionByCategoryAndId("science", 1L);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(dto, response.getBody());
  }

  @Test
  void getQuestionByCategoryAndId_shouldReturnNotFound_whenCategoryMatchesButIdDoesNot() {
    Question question = Question.builder().questionId(2).topic("Science").build();

    when(questionRepository.findAll()).thenReturn(List.of(question));

    ResponseEntity<QuestionDto> response = questionService.getQuestionByCategoryAndId("science", 1L);

    assertEquals(404, response.getStatusCodeValue());
  }

  @Test
  void getQuestionByCategoryAndId_shouldReturnNotFound_whenIdMatchesButCategoryDoesNot() {
    Question question = Question.builder().questionId(1).topic("Math").build();

    when(questionRepository.findAll()).thenReturn(List.of(question));

    ResponseEntity<QuestionDto> response = questionService.getQuestionByCategoryAndId("science", 1L);

    assertEquals(404, response.getStatusCodeValue());
  }

//  @Test
//  void getQuestionByCategoryAndId_shouldReturnNotFound_whenNoMatch() {
//    Question question = Question.builder().questionId(2).topic("Math").build();
//
//    when(questionRepository.findAll()).thenReturn(List.of(question));
//
//    ResponseEntity<QuestionDto> response = questionService.getQuestionByCategoryAndId("science", 1L);
//
//    assertEquals(404, response.getStatusCodeValue());
//  }

}