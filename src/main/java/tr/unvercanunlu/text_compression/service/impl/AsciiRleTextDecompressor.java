package tr.unvercanunlu.text_compression.service.impl;

import tr.unvercanunlu.text_compression.service.Decompressor;
import tr.unvercanunlu.text_compression.util.CharacterUtil;
import tr.unvercanunlu.text_compression.util.ValidationUtil;

public class AsciiRleTextDecompressor implements Decompressor<String, String> {

  private static final String NON_ASCII_LETTER_ERROR_MESSAGE = "Invalid input: expected ASCII letter at position %d: '%c'";
  private static final String NON_ASCII_DIGIT_ERROR_MESSAGE = "Invalid input: expected ASCII digit at position %d but got '%c'";
  private static final String NON_ASCII_CHARACTER_ERROR_MESSAGE = "Invalid input: expected ASCII letter or digit at position %d but got '%c'";

  @Override
  public String decompress(String input) {
    // validation
    ValidationUtil.validateInput(input);

    // empty and single-length case
    if (input.isEmpty()) {
      return "";
    }

    // first
    char first = input.charAt(0);

    // validation
    if (!CharacterUtil.isAsciiAlphabetic(first)) {
      throw new IllegalArgumentException(NON_ASCII_LETTER_ERROR_MESSAGE.formatted(0, first));
    }

    if (input.length() == 1) {
      return input;
    }

    // length
    int length = input.length();

    // initialize
    StringBuilder builder = new StringBuilder();

    // reset
    int count = 1;
    char previous = first;
    boolean isPreviousAlphabetic = true;

    // loop
    for (int i = 1; i < length; i++) {
      char current = input.charAt(i);

      if (CharacterUtil.isAsciiAlphabetic(current)) {
        // append
        appendGroup(builder, previous, count);

        // reset
        count = 1;
        previous = current;
        isPreviousAlphabetic = true;

      } else if (CharacterUtil.isAsciiNumeric(current)) {
        int converted = CharacterUtil.convertNumericDigit(current);

        // validation for leading zero case
        if (isPreviousAlphabetic && converted == 0) {
          throw new IllegalArgumentException(NON_ASCII_DIGIT_ERROR_MESSAGE.formatted(i, current));
        }

        if (isPreviousAlphabetic) {
          count = converted;
        } else {
          count = (count * 10) + converted;
        }

        isPreviousAlphabetic = false;

      }
      // validation
      else {
        throw new IllegalArgumentException(NON_ASCII_CHARACTER_ERROR_MESSAGE.formatted(i, current));
      }
    }

    // append
    appendGroup(builder, previous, count);

    // finalize
    return builder.toString();
  }

  private static void appendGroup(StringBuilder builder, char last, int count) {
    for (int j = 0; j < count; j++) {
      builder.append(last);
    }
  }

}
