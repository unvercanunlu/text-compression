package tr.unvercanunlu.text_compression.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CharacterUtil {

  public static boolean isAsciiAlphabetic(char character) {
    return ((character >= 'a') && (character <= 'z')) || ((character >= 'A') && (character <= 'Z'));
  }

  public static boolean isAsciiNumeric(char character) {
    return (character >= '0') && (character <= '9');
  }

  public static int convertNumericDigit(char character) {
    return character - '0';
  }

}
