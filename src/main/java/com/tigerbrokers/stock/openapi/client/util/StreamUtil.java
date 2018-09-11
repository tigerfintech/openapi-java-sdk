package com.tigerbrokers.stock.openapi.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class StreamUtil {

  private static final int DEFAULT_BUFFER_SIZE = 8192;

  public static void io(InputStream in, OutputStream out) throws IOException {
    io(in, out, -1);
  }

  public static void io(InputStream in, OutputStream out, int bufferSize) throws IOException {
    if (bufferSize == -1) {
      bufferSize = DEFAULT_BUFFER_SIZE;
    }

    byte[] buffer = new byte[bufferSize];
    int amount;

    while ((amount = in.read(buffer)) >= 0) {
      out.write(buffer, 0, amount);
    }
  }

  public static void io(Reader in, Writer out) throws IOException {
    io(in, out, -1);
  }

  public static void io(Reader in, Writer out, int bufferSize) throws IOException {
    if (bufferSize == -1) {
      bufferSize = DEFAULT_BUFFER_SIZE >> 1;
    }

    char[] buffer = new char[bufferSize];
    int amount;

    while ((amount = in.read(buffer)) >= 0) {
      out.write(buffer, 0, amount);
    }
  }

  public static String readText(InputStream in) throws IOException {
    return readText(in, null, -1);
  }

  public static String readText(InputStream in, String encoding) throws IOException {
    return readText(in, encoding, -1);
  }

  public static String readText(InputStream in, String encoding, int bufferSize)
      throws IOException {
    Reader reader = (encoding == null) ? new InputStreamReader(in) : new InputStreamReader(in,
        encoding);

    return readText(reader, bufferSize);
  }

  public static String readText(Reader reader) throws IOException {
    return readText(reader, -1);
  }

  public static String readText(Reader reader, int bufferSize) throws IOException {
    StringWriter writer = new StringWriter();

    io(reader, writer, bufferSize);
    return writer.toString();
  }
}
