package tr.unvercanunlu.text_compression.service.impl;

import static tr.unvercanunlu.text_compression.config.Message.NON_ASCII_CHARACTER_ERROR_MESSAGE;
import static tr.unvercanunlu.text_compression.config.Message.NON_ASCII_DIGIT_ERROR_MESSAGE;
import static tr.unvercanunlu.text_compression.config.Message.NON_ASCII_LETTER_ERROR_MESSAGE;

import lombok.extern.slf4j.Slf4j;
import tr.unvercanunlu.text_compression.service.Decompressor;
import tr.unvercanunlu.text_compression.util.CharacterUtil;
import tr.unvercanunlu.text_compression.util.ValidationUtil;

@Slf4j
public class AsciiRleTextDecompressor implements Decompressor<String, String> {

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
      String message = NON_ASCII_LETTER_ERROR_MESSAGE.formatted(0, first);
      log.error(message);
      throw new IllegalArgumentException(message);
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
          String message = NON_ASCII_DIGIT_ERROR_MESSAGE.formatted(i, current);
          log.error(message);
          throw new IllegalArgumentException(message);
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
        String message = NON_ASCII_CHARACTER_ERROR_MESSAGE.formatted(i, current);
        log.error(message);
        throw new IllegalArgumentException(message);
      }
    }

    // append
    appendGroup(builder, previous, count);

    // finalize
    String output = builder.toString();

    log.debug("Text decompressed: input=%s output=%s".formatted(input, output));

    return output;
  }

  private static void appendGroup(StringBuilder builder, char last, int count) {
    for (int j = 0; j < count; j++) {
      builder.append(last);
    }
  }

}
