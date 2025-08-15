package tr.unvercanunlu.text_compression.service.impl;

import static tr.unvercanunlu.text_compression.config.Message.NON_ASCII_LETTER_ERROR_MESSAGE;

import lombok.extern.slf4j.Slf4j;
import tr.unvercanunlu.text_compression.service.Compressor;
import tr.unvercanunlu.text_compression.util.CharacterUtil;
import tr.unvercanunlu.text_compression.util.ValidationUtil;

@Slf4j
public class AsciiRleTextCompressor implements Compressor<String, String> {

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
      String message = NON_ASCII_LETTER_ERROR_MESSAGE.formatted(0, first);
      log.error(message);
      throw new IllegalArgumentException(message);
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
        String message = NON_ASCII_LETTER_ERROR_MESSAGE.formatted(i, current);
        log.error(message);
        throw new IllegalArgumentException(message);
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
    String output = builder.toString();

    log.debug("Text compressed: input=%s output=%s".formatted(input, output));

    return output;
  }

  private static void appendGroup(StringBuilder builder, char character, int count) {
    builder.append(character);

    if (count > 1) {
      builder.append(count);
    }
  }

}
