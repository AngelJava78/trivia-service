package com.nttdata.services.trivia.controller;

import com.nttdata.services.trivia.dto.CategoryCountDto;
import com.nttdata.services.trivia.dto.QuestionDto;
import com.nttdata.services.trivia.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;

  @GetMapping("list")
  public ResponseEntity<List<QuestionDto>> getUserList() {
    return questionService.getAllQuestions();
  }

  @GetMapping("{category}")
  public ResponseEntity<List<QuestionDto>> getQuestionsByCategory(@PathVariable String category) {
    return questionService.getQuestionsByCategory(category);
  }

  @GetMapping("{category}/{id}")
  public ResponseEntity<QuestionDto> getQuestionByCategoryAndId(@PathVariable String category,
      @PathVariable Long id) {
    return questionService.getQuestionByCategoryAndId(category, id);
  }

  @PostMapping
  public ResponseEntity<QuestionDto> saveQuestion(@RequestBody QuestionDto questionDto) {
    return questionService.saveQuestion(questionDto);
  }


  @GetMapping("count")
  public ResponseEntity<List<CategoryCountDto>> getCategories() {
    return questionService.getCategories();
  }


}
