package com.nttdata.services.trivia.service;

import com.nttdata.services.trivia.dto.CategoryCountDto;
import com.nttdata.services.trivia.dto.QuestionDto;
import com.nttdata.services.trivia.mappers.QuestionMapper;
import com.nttdata.services.trivia.model.Question;
import com.nttdata.services.trivia.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {

  private final QuestionRepository questionRepository;

  private final QuestionMapper questionMapper;

  public ResponseEntity<List<QuestionDto>> getAllQuestions() {
    List<QuestionDto> questionList = questionRepository.findAll()
        .stream()
        .map(questionMapper::toDto)
        .collect(Collectors.toList());
    return ResponseEntity.ok(questionList);
  }

  public ResponseEntity<List<QuestionDto>> getQuestionsByCategory(String category) {
    List<QuestionDto> questionsByCategory = questionRepository.findAll()
        .stream()
        .filter(q -> q.getTopic().equalsIgnoreCase(category))
        .map(questionMapper::toDto)
        .collect(Collectors.toList());

    return ResponseEntity.ok(questionsByCategory);
  }

  public ResponseEntity<QuestionDto> getQuestionByCategoryAndId(String category, Long id) {
    Optional<Question> questionOptional = questionRepository.findAll()
        .stream()
        .filter(q -> q.getTopic().equalsIgnoreCase(category) && q.getQuestionId() == id)
        .findFirst();

    return questionOptional
        .map(question -> ResponseEntity.ok(questionMapper.toDto(question)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }


  public ResponseEntity<QuestionDto> saveQuestion(QuestionDto questionDto) {
    Question question = questionMapper.toModel(questionDto);
    Question savedQuestion = questionRepository.save(question);
    return ResponseEntity.ok(questionMapper.toDto(savedQuestion));
  }

  public ResponseEntity<List<CategoryCountDto>> getCategories() {
    List<Question> allQuestions = questionRepository.findAll();

    Map<String, Long> grouped = allQuestions.stream()
        .collect(Collectors.groupingBy(
            q -> q.getTopic().toLowerCase(),
            Collectors.counting()
        ));

    List<CategoryCountDto> result = grouped.entrySet().stream()
        .map(entry -> new CategoryCountDto(entry.getKey(), entry.getValue()))
        .collect(Collectors.toList());

    return ResponseEntity.ok(result);
  }


}
