package com.nttdata.services.trivia.repository;

import com.nttdata.services.trivia.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {


    List<Question> findByTopicIgnoreCase(String topic);

    Optional<Question> findByTopicIgnoreCaseAndQuestionId(String topic, int questionId);

}
