package com.nttdata.services.trivia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class QuestionTest {

  @Test
  void getOptions_shouldReturnUnmodifiableList_whenOptionsIsNotNull() {
    List<String> inputOptions = Arrays.asList("Option 1", "Option 2");
    Question dto = Question.builder().build();
    dto.setOptions(inputOptions);

    List<String> result = dto.getOptions();

    assertNotNull(result);
    assertEquals(inputOptions, result);
    assertThrows(UnsupportedOperationException.class, () -> result.add("Option 3"));
  }

  @Test
  void getOptions_shouldReturnNull_whenOptionsIsNull() {
    Question dto = Question.builder().build();
    dto.setOptions(null);

    assertNull(dto.getOptions());
  }

  @Test
  void setOptions_shouldCreateDefensiveCopy_whenListIsProvided() {
    List<String> original = new ArrayList<>(Arrays.asList("A", "B"));
    Question dto = Question.builder().build();
    dto.setOptions(original);

    original.set(0, "Modified");

    List<String> result = dto.getOptions();
    assertEquals("A", result.get(0), "La copia defensiva debe evitar modificaciones externas");
  }

  @Test
  void setOptions_shouldSetNull_whenNullIsProvided() {
    Question dto = Question.builder().build();
    dto.setOptions(null);

    assertNull(dto.getOptions());
  }
}