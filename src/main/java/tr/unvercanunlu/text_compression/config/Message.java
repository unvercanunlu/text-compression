package tr.unvercanunlu.text_compression.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {

  public static final String NON_ASCII_LETTER_ERROR_MESSAGE = "Invalid input: expected ASCII letter at position %d: '%c'";
  public static final String NON_ASCII_DIGIT_ERROR_MESSAGE = "Invalid input: expected ASCII digit at position %d but got '%c'";
  public static final String NON_ASCII_CHARACTER_ERROR_MESSAGE = "Invalid input: expected ASCII letter or digit at position %d but got '%c'";

}
