package com.nttdata.services.trivia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application.
 *
 * @author <a href="ajavierv@emeal.nttdata.com">ajavierv@emeal.nttdata.com</a>
 */
@SpringBootApplication
public class TriviaServiceApplication {

  /**
   * main method to launch the application.
   *
   * @param args input arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(TriviaServiceApplication.class, args);
  }

}
