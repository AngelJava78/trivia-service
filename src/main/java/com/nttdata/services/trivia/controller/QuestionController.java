package com.nttdata.services.trivia.controller;

import com.nttdata.services.trivia.dto.CategoryCountDto;
import com.nttdata.services.trivia.dto.QuestionDto;
import com.nttdata.services.trivia.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Question controller.
 *
 * @author <a href="ajavierv@emeal.nttdata.com">ajavierv@emeal.nttdata.com</a>
 */
@Slf4j
@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;

  /**
   * Get all questions.
   *
   * @return ResponseEntity with list of questions.
   */
  @GetMapping("list")
  public ResponseEntity<List<QuestionDto>> getAllQuestions() {
    return questionService.getAllQuestions();
  }

  /**
   * Get questions by category.
   *
   * @param category category name.
   * @return ResponseEntity with list of questions.
   */
  @GetMapping("{category}")
  public ResponseEntity<List<QuestionDto>> getQuestionsByCategory(@PathVariable String category) {
    return questionService.getQuestionsByCategory(category);
  }

  /**
   * Get all questions.
   *
   * @param category category name.
   * @param id question id.
   * @return ResponseEntity with list of questions.
   */
  @GetMapping("{category}/{id}")
  public ResponseEntity<QuestionDto> getQuestionByCategoryAndId(@PathVariable String category,
      @PathVariable Long id) {
    return questionService.getQuestionByCategoryAndId(category, id);
  }

  /**
   * Save question.
   *
   * @param questionDto question input data.
   * @return ResponseEntity with saved question.
   */
  @PostMapping
  public ResponseEntity<QuestionDto> saveQuestion(@RequestBody QuestionDto questionDto) {
    return questionService.saveQuestion(questionDto);
  }

  /**
   * Get question summary.
   *
   * @return ResponseEntity with question summary grouped by category.
   */
  @GetMapping("count")
  public ResponseEntity<List<CategoryCountDto>> getCategories() {
    return questionService.getCategories();
  }


}
