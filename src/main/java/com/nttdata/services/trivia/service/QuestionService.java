package com.nttdata.services.trivia.service;

import com.nttdata.services.trivia.dto.CategoryCountDto;
import com.nttdata.services.trivia.dto.QuestionDto;
import com.nttdata.services.trivia.mappers.QuestionMapper;
import com.nttdata.services.trivia.model.Question;
import com.nttdata.services.trivia.repository.QuestionRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Question service.
 *
 * @author <a href="ajavierv@emeal.nttdata.com">ajavierv@emeal.nttdata.com</a>
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {

  private final QuestionRepository questionRepository;

  private final QuestionMapper questionMapper;

  /**
   * Get all questions.
   *
   * @return ResponseEntity with list of questions
   */
  public ResponseEntity<List<QuestionDto>> getAllQuestions() {
    List<QuestionDto> questionList = questionRepository.findAll()
        .stream()
        .map(questionMapper::toDto)
        .toList();
    return ResponseEntity.ok(questionList);
  }

  /**
   * Get questions by category.
   *
   * @param category category name
   * @return ResponseEntity with list of questions
   */
  public ResponseEntity<List<QuestionDto>> getQuestionsByCategory(String category) {
    List<QuestionDto> questionsByCategory = questionRepository.findAll()
        .stream()
        .filter(q -> q.getTopic().equalsIgnoreCase(category))
        .map(questionMapper::toDto)
        .toList();

    return ResponseEntity.ok(questionsByCategory);
  }

  /**
   * Get all questions.
   *
   * @param category category name
   * @param id question id
   * @return ResponseEntity with list of questions
   */
  public ResponseEntity<QuestionDto> getQuestionByCategoryAndId(String category, Long id) {
    Optional<Question> questionOptional = questionRepository.findAll()
        .stream()
        .filter(q -> q.getTopic().equalsIgnoreCase(category) && q.getQuestionId() == id)
        .findFirst();

    return questionOptional
        .map(question -> ResponseEntity.ok(questionMapper.toDto(question)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Save question.
   *
   * @param questionDto question input data
   * @return ResponseEntity with saved question
   */
  public ResponseEntity<QuestionDto> saveQuestion(QuestionDto questionDto) {
    Question question = questionMapper.toModel(questionDto);
    Question savedQuestion = questionRepository.save(question);
    return ResponseEntity.ok(questionMapper.toDto(savedQuestion));
  }

  /**
   * Get question summary.
   *
   * @return ResponseEntity with question summary grouped by category
   */
  public ResponseEntity<List<CategoryCountDto>> getCategories() {
    List<Question> allQuestions = questionRepository.findAll();

    Map<String, Long> grouped = allQuestions.stream()
        .collect(Collectors.groupingBy(
            q -> q.getTopic().toLowerCase(),
            Collectors.counting()
        ));

    List<CategoryCountDto> result = grouped.entrySet().stream()
        .map(entry -> new CategoryCountDto(entry.getKey(), entry.getValue()))
        .toList();

    return ResponseEntity.ok(result);
  }


}
