package tr.unvercanunlu.text_compression.service.impl;

import tr.unvercanunlu.text_compression.service.Compressor;
import tr.unvercanunlu.text_compression.util.ValidationUtil;

public class RleTextCompressor implements Compressor<String, String> {

  @Override
  public String compress(String input) {
    // validation
    ValidationUtil.validateInput(input);

    // empty case
    if (input.isEmpty()) {
      return "";
    }

    // single-length case
    if (input.length() == 1) {
      return input;
    }

    // initialize
    StringBuilder builder = new StringBuilder();

    // reset
    int count = 1;
    char last = input.charAt(0);

    // loop
    for (int i = 1; i < input.length(); i++) {
      char character = input.charAt(i);

      // comparison
      if (last == character) {
        count++;
      } else {
        writeLast(builder, last, count);

        // reset
        count = 1;
        last = character;
      }
    }

    writeLast(builder, last, count);

    return builder.toString();
  }

  private static void writeLast(StringBuilder builder, char last, int count) {
    builder.append(last);
    if (count > 1) {
      builder.append(count);
    }
  }

}
