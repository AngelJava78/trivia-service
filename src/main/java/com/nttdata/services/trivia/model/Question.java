package com.nttdata.services.trivia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model Object representing a trivia question. Contains the ID, question ID, topic, question text,
 * list of options, and the correct answer.
 *
 * <p>This class includes defensive copying to prevent external modification of internal state.</p>
 *
 * @author <a href="ajavierv@emeal.nttdata.com">ajavierv@emeal.nttdata.com</a>
 */
@Data
@Builder
@Document(collection = "questions")
public class Question {


  /**
   * Unique identifier for the question.
   */
  @Id
  private String id;

  /**
   * Question id.
   */
  private int questionId;

  /**
   * Topic or category of the question.
   */
  private String topic;

  /**
   * Text of the question.
   */
  private String text;

  /**
   * List of possible answer options.
   */
  private List<String> options;

  /**
   * Correct answer to the question.
   */
  private String answer;

  /**
   * Returns an unmodifiable view of the options list.
   *
   * @return an unmodifiable list of options, or {@code null} if options are not set
   */
  public List<String> getOptions() {
    return options == null ? null : Collections.unmodifiableList(options);
  }

  /**
   * Sets the options list with a defensive copy.
   *
   * @param options the list of options to set
   */
  public void setOptions(List<String> options) {
    this.options = options == null ? null : new ArrayList<>(options);
  }

}
