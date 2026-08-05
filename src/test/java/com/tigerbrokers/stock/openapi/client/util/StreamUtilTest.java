package com.tigerbrokers.stock.openapi.client.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.Assert;
import org.junit.Test;

public class StreamUtilTest {

  @Test
  public void testIo_defaultBufferSize() throws IOException {
    byte[] data = "hello world".getBytes();
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    StreamUtil.io(in, out);
    Assert.assertArrayEquals(data, out.toByteArray());
  }
  @Test
  public void testIo_customBufferSize() throws IOException {
    byte[] data = "hello world".getBytes();
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    StreamUtil.io(in, out, 4);
    Assert.assertArrayEquals(data, out.toByteArray());
  }
  @Test
  public void testIo_emptyInput() throws IOException {
    ByteArrayInputStream in = new ByteArrayInputStream(new byte[0]);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    StreamUtil.io(in, out);
    Assert.assertEquals(0, out.toByteArray().length);
  }
  @Test
  public void testIo_largeData() throws IOException {
    byte[] data = new byte[10000];
    for (int i = 0; i < data.length; i++) { data[i] = (byte) (i % 256); }
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    StreamUtil.io(in, out);
    Assert.assertArrayEquals(data, out.toByteArray());
  }
  @Test
  public void testIoReaderWriter_defaultBufferSize() throws IOException {
    StringReader in = new StringReader("hello world");
    StringWriter out = new StringWriter();
    StreamUtil.io(in, out);
    Assert.assertEquals("hello world", out.toString());
  }
  @Test
  public void testIoReaderWriter_customBufferSize() throws IOException {
    StringReader in = new StringReader("hello world");
    StringWriter out = new StringWriter();
    StreamUtil.io(in, out, 4);
    Assert.assertEquals("hello world", out.toString());
  }
  @Test
  public void testReadText_inputStream() throws IOException {
    byte[] data = "hello world".getBytes();
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    String result = StreamUtil.readText(in);
    Assert.assertEquals("hello world", result);
  }
  @Test
  public void testReadText_inputStreamWithEncoding() throws IOException {
    byte[] data = "hello world".getBytes("UTF-8");
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    String result = StreamUtil.readText(in, "UTF-8");
    Assert.assertEquals("hello world", result);
  }
  @Test
  public void testReadText_inputStreamWithEncodingAndBufferSize() throws IOException {
    byte[] data = "hello world".getBytes("UTF-8");
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    String result = StreamUtil.readText(in, "UTF-8", 4);
    Assert.assertEquals("hello world", result);
  }
  @Test
  public void testReadText_reader() throws IOException {
    StringReader reader = new StringReader("hello world");
    String result = StreamUtil.readText(reader);
    Assert.assertEquals("hello world", result);
  }
  @Test
  public void testReadText_readerWithBufferSize() throws IOException {
    StringReader reader = new StringReader("hello world");
    String result = StreamUtil.readText(reader, 4);
    Assert.assertEquals("hello world", result);
  }
  @Test
  public void testReadText_emptyStream() throws IOException {
    ByteArrayInputStream in = new ByteArrayInputStream(new byte[0]);
    String result = StreamUtil.readText(in);
    Assert.assertEquals("", result);
  }
}
