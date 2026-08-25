package com.tigerbrokers.stock.openapi.client.util.codec;

import org.junit.Assert;
import org.junit.Test;

public class CodecExceptionTest {

  @Test
  public void testDecoderException_message() {
    DecoderException ex = new DecoderException("test message");
    Assert.assertEquals("test message", ex.getMessage());
  }

  @Test
  public void testDecoderException_nullMessage() {
    DecoderException ex = new DecoderException(null);
    Assert.assertNull(ex.getMessage());
  }

  @Test
  public void testDecoderException_isException() {
    DecoderException ex = new DecoderException("error");
    Assert.assertTrue(ex instanceof Exception);
  }

  @Test
  public void testEncoderException_message() {
    EncoderException ex = new EncoderException("test message");
    Assert.assertEquals("test message", ex.getMessage());
  }

  @Test
  public void testEncoderException_nullMessage() {
    EncoderException ex = new EncoderException(null);
    Assert.assertNull(ex.getMessage());
  }

  @Test
  public void testEncoderException_isException() {
    EncoderException ex = new EncoderException("error");
    Assert.assertTrue(ex instanceof Exception);
  }
}
