package com.nttdata.services.trivia.repository;

import com.nttdata.services.trivia.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Question mongo repository.
 *
 * @author <a href="ajavierv@emeal.nttdata.com">ajavierv@emeal.nttdata.com</a>
 */
@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {

}
