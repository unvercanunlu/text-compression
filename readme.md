**ASCII RLE Text Compression**  
A Java implementation of **Run-Length Encoding (RLE)** for ASCII alphabetic text. Supports compression and decompression with strict validation rules.

**Features**

* Compress consecutive repeated letters into `{letter}{count}` format
* Decompress back to original string
* Validates input:

    * Only ASCII letters allowed in uncompressed text
    * Only ASCII letters/digits allowed in compressed text
    * No leading zeros in counts
* Handles empty and single-character inputs
* Utility classes for ASCII checks and validation

**API**

```java
// Compression
Compressor<String, String> compressor = new AsciiRleTextCompressor();
String compressed = compressor.compress("AAABBC"); // "A3B2C"

// Decompression
Decompressor<String, String> decompressor = new AsciiRleTextDecompressor();
String original = decompressor.decompress("A3B2C"); // "AAABBC"
```

**Example**

```java
Compressor<String, String> c = new AsciiRleTextCompressor();
System.out.println(c.compress("AAABB")); // A3B2

Decompressor<String, String> d = new AsciiRleTextDecompressor();
System.out.println(d.decompress("A3B2")); // AAABB
```
