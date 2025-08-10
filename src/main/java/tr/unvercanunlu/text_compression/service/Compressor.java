package tr.unvercanunlu.text_compression.service;

public interface Compressor<I, O> {

  O compress(I input);

}
