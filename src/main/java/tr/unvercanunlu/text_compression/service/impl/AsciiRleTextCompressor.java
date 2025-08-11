package tr.unvercanunlu.text_compression.service.impl;

import tr.unvercanunlu.text_compression.service.Compressor;
import tr.unvercanunlu.text_compression.util.CharacterUtil;
import tr.unvercanunlu.text_compression.util.ValidationUtil;

public class AsciiRleTextCompressor implements Compressor<String, String> {

  private static final String NON_ASCII_ERROR_MESSAGE = "Invalid input: expected ASCII letter at position %d: '%s'";

  @Override
  public String compress(String input) {
    // validation
    ValidationUtil.validateInput(input);

    // empty case
    if (input.isEmpty()) {
      return "";
    }

    // first
    char first = input.charAt(0);

    // validation
    if (!CharacterUtil.isAsciiAlphabetic(first)) {
      throw new IllegalArgumentException(NON_ASCII_ERROR_MESSAGE.formatted(0, first));
    }

    // length
    int length = input.length();

    // single-length case
    if (length == 1) {
      return input;
    }

    // initialize
    StringBuilder builder = new StringBuilder(length);

    // reset
    int count = 1;
    char previous = first;

    // loop
    for (int i = 1; i < length; i++) {
      char current = input.charAt(i);

      // validation
      if (!CharacterUtil.isAsciiAlphabetic(current)) {
        throw new IllegalArgumentException(NON_ASCII_ERROR_MESSAGE.formatted(i, current));
      }

      // comparison
      if (previous != current) {
        // append
        appendGroup(builder, previous, count);

        // reset
        count = 1;
        previous = current;

      } else {
        count++;
      }
    }

    // append
    appendGroup(builder, previous, count);

    // finalize
    return builder.toString();
  }

  private static void appendGroup(StringBuilder builder, char character, int count) {
    builder.append(character);

    if (count > 1) {
      builder.append(count);
    }
  }

}
