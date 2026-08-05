package com.tigerbrokers.stock.openapi.client.util.codec;
import org.junit.Assert;
import org.junit.Test;
public class Base64Test {
  @Test
  public void testEncodeBase64_simple() { Assert.assertEquals("aGVsbG8=", new String(Base64.encodeBase64("hello".getBytes()))); }
  @Test
  public void testEncodeBase64_empty() { Assert.assertEquals(0, Base64.encodeBase64(new byte[0]).length); }
  @Test
  public void testEncodeBase64_oneByte() { Assert.assertEquals("Zg==", new String(Base64.encodeBase64("f".getBytes()))); }
  @Test
  public void testEncodeBase64_twoBytes() { Assert.assertEquals("Zm8=", new String(Base64.encodeBase64("fo".getBytes()))); }
  @Test
  public void testEncodeBase64_threeBytes() { Assert.assertEquals("Zm9v", new String(Base64.encodeBase64("foo".getBytes()))); }
  @Test
  public void testEncodeBase64Chunked_largeData() {
    byte[] data = new byte[200]; for (int i=0;i<data.length;i++) data[i]=(byte)(i%128);
    Assert.assertArrayEquals(data, Base64.decodeBase64(Base64.encodeBase64Chunked(data)));
  }
  @Test
  public void testEncodeBase64_withChunkFlag() {
    byte[] data = "hello".getBytes();
    Assert.assertArrayEquals(data, Base64.decodeBase64(Base64.encodeBase64(data, false)));
    Assert.assertArrayEquals(data, Base64.decodeBase64(Base64.encodeBase64(data, true)));
  }
  @Test
  public void testDecodeBase64_simple() { Assert.assertEquals("hello", new String(Base64.decodeBase64("aGVsbG8=".getBytes()))); }
  @Test
  public void testDecodeBase64_withWhitespace() { Assert.assertEquals("hello", new String(Base64.decodeBase64("aGV s bG 8=".getBytes()))); }
  @Test
  public void testDecodeBase64_empty() { Assert.assertEquals(0, Base64.decodeBase64("".getBytes()).length); }
  @Test
  public void testDecodeBase64_allPad() { Assert.assertEquals(0, Base64.decodeBase64("====".getBytes()).length); }
  @Test
  public void testDecodeBase64_twoPads() { Assert.assertEquals("f", new String(Base64.decodeBase64("Zg==".getBytes()))); }
  @Test
  public void testDecodeBase64_onePad() { Assert.assertEquals("fo", new String(Base64.decodeBase64("Zm8=".getBytes()))); }
  @Test
  public void testDecodeBase64_noPad() { Assert.assertEquals("foo", new String(Base64.decodeBase64("Zm9v".getBytes()))); }
  @Test
  public void testIsArrayByteBase64_valid() { Assert.assertTrue(Base64.isArrayByteBase64("aGVsbG8=".getBytes())); }
  @Test
  public void testIsArrayByteBase64_invalid() { Assert.assertFalse(Base64.isArrayByteBase64("hello!".getBytes())); }
  @Test
  public void testIsArrayByteBase64_empty() { Assert.assertTrue(Base64.isArrayByteBase64(new byte[0])); }
  @Test
  public void testEncodeObject_byteArray() throws Exception {
    Base64 b = new Base64(); Object r = b.encode((Object)"test".getBytes()); Assert.assertTrue(r instanceof byte[]);
  }
  @Test(expected = EncoderException.class)
  public void testEncodeObject_nonByteArray() throws Exception { new Base64().encode((Object)"string"); }
  @Test
  public void testDecodeObject_byteArray() throws Exception {
    Base64 b = new Base64(); byte[] e = Base64.encodeBase64("test".getBytes());
    Assert.assertEquals("test", new String((byte[])b.decode((Object)e)));
  }
  @Test(expected = DecoderException.class)
  public void testDecodeObject_nonByteArray() throws Exception { new Base64().decode((Object)"string"); }
  @Test
  public void testEncodeByteArray() { Base64 b = new Base64(); Assert.assertEquals(new String(Base64.encodeBase64("test".getBytes())), new String(b.encode("test".getBytes()))); }
  @Test
  public void testDecodeByteArray() { Base64 b = new Base64(); Assert.assertEquals("test", new String(b.decode(Base64.encodeBase64("test".getBytes())))); }
  @Test
  public void testRoundTrip_randomData() {
    for (int s=1;s<=100;s++) { byte[] d=new byte[s]; for(int i=0;i<s;i++) d[i]=(byte)(i*7+13); Assert.assertArrayEquals(d, Base64.decodeBase64(Base64.encodeBase64(d))); }
  }
}
