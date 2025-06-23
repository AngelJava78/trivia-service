package com.nttdata.services.trivia.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CategoryCountDto.
 *
 * @author <a href="ajavierv@emeal.nttdata.com">ajavierv@emeal.nttdata.com</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCountDto {

  /**
   * topic name.
   */
  private String topic;

  /**
   * total count grouped by topic name.
   */

  private long total;

}
