package com.nttdata.services.trivia.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionDto {

  private int questionId;
  private String topic;
  private String question;
  private List<String> options;
  private String answer;
}
