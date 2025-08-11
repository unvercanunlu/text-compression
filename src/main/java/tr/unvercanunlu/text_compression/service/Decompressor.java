package tr.unvercanunlu.text_compression.service;

public interface Decompressor<I,O> {

  O decompress(I input);

}
