package com.nttdata.services.trivia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nttdata.services.trivia.dto.CategoryCountDto;
import com.nttdata.services.trivia.dto.QuestionDto;
import com.nttdata.services.trivia.service.QuestionService;
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
class QuestionControllerTest {

  @Mock
  private QuestionService questionService;

  @InjectMocks
  private QuestionController questionController;

  QuestionDto createScienceQuestionDto() {
    return QuestionDto.builder()
        .questionId(1)
        .topic("Science")
        .text("¿Cuál es el planeta más cercano al sol?")
        .options(List.of("Venus", "Marte", "Mercurio", "Júpiter"))
        .answer("Mercurio")
        .build();
  }

  QuestionDto createFoodQuestionDto() {
    return QuestionDto.builder()
        .questionId(2)
        .topic("Food")
        .text("¿Cuál es el ingrediente principal del guacamole?")
        .options(Arrays.asList("Tomate", "Aguacate", "Cebolla", "Chile"))
        .answer("Aguacate")
        .build();
  }

  @Test
  void getAllQuestions_shouldReturnListOfQuestionDtos_whenServiceReturnsQuestions() {
    // Arrange
    QuestionDto question1 = createScienceQuestionDto();

    QuestionDto question2 = createFoodQuestionDto();

    List<QuestionDto> expectedList = List.of(question1, question2);
    when(questionService.getAllQuestions()).thenReturn(ResponseEntity.ok(expectedList));

    // Act
    ResponseEntity<List<QuestionDto>> response = questionController.getAllQuestions();

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(expectedList, response.getBody());
    verify(questionService, times(1)).getAllQuestions();
  }

  @Test
  void getQuestionsByCategory_shouldReturnListOfQuestionDtos_whenCategoryMatches() {
    // Arrange
    String category = "Science";
    QuestionDto question = createScienceQuestionDto();
    List<QuestionDto> expectedList = List.of(question);

    when(questionService.getQuestionsByCategory(category)).thenReturn(
        ResponseEntity.ok(expectedList));

    // Act
    ResponseEntity<List<QuestionDto>> response = questionController.getQuestionsByCategory(
        category);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(expectedList, response.getBody());
    verify(questionService, times(1)).getQuestionsByCategory(category);
  }

  @Test
  void getQuestionByCategoryAndId_shouldReturnQuestionDto_whenCategoryAndIdMatch() {
    // Arrange
    String category = "Science";
    Long id = 1L;
    QuestionDto expectedDto = createScienceQuestionDto();

    when(questionService.getQuestionByCategoryAndId(category, id)).thenReturn(
        ResponseEntity.ok(expectedDto));

    // Act
    ResponseEntity<QuestionDto> response = questionController.getQuestionByCategoryAndId(category,
        id);

    // Assert
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(expectedDto, response.getBody());
    verify(questionService, times(1)).getQuestionByCategoryAndId(category, id);
  }

  @Test
  void getQuestionByCategoryAndId_shouldReturnNotFound_whenCategoryAndIdNotMatch() {
// Arrange
    String category = "History";
    Long id = 1L;

    when(questionService.getQuestionByCategoryAndId(category, id))
        .thenReturn(ResponseEntity.notFound().build());

    // Act
    ResponseEntity<QuestionDto> response = questionController.getQuestionByCategoryAndId(category,
        id);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
    verify(questionService, times(1)).getQuestionByCategoryAndId(category, id);
  }

  @Test
  void saveQuestion_shouldReturnSavedQuestionDto_whenInputIsValid() {
    // Arrange
    QuestionDto inputDto = createScienceQuestionDto();
    QuestionDto savedDto = inputDto; // Simulamos que el guardado devuelve el mismo objeto

    when(questionService.saveQuestion(inputDto)).thenReturn(ResponseEntity.ok(savedDto));

    // Act
    ResponseEntity<QuestionDto> response = questionController.saveQuestion(inputDto);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(savedDto, response.getBody());
    verify(questionService, times(1)).saveQuestion(inputDto);
  }

  @Test
  void getCategories_shouldReturnListOfCategoryCountDto_whenServiceReturnsData() {
    // Arrange
    List<CategoryCountDto> expectedList = List.of(
        new CategoryCountDto("science", 3L),
        new CategoryCountDto("food", 2L)
    );

    when(questionService.getCategories()).thenReturn(ResponseEntity.ok(expectedList));

    // Act
    ResponseEntity<List<CategoryCountDto>> response = questionController.getCategories();

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedList, response.getBody());
    verify(questionService, times(1)).getCategories();
  }
}
