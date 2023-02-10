package com.tigerbrokers.stock.openapi.client.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.stomp.DefaultLastStompContentSubframe;
import io.netty.handler.codec.stomp.DefaultStompContentSubframe;
import io.netty.handler.codec.stomp.DefaultStompHeadersSubframe;
import io.netty.handler.codec.stomp.LastStompContentSubframe;
import io.netty.handler.codec.stomp.StompCommand;
import io.netty.handler.codec.stomp.StompContentSubframe;
import io.netty.handler.codec.stomp.StompHeaders;
import io.netty.handler.codec.stomp.StompHeadersSubframe;
import io.netty.util.internal.AppendableCharSequence;
import java.util.List;
import java.util.Locale;

import static io.netty.buffer.ByteBufUtil.indexOf;
import static io.netty.buffer.ByteBufUtil.readBytes;

/**
 * @author zhaolei
 * create at 2018/12/20
 */
public class WebSocketStompFrameDecoder extends MessageToMessageDecoder<WebSocketFrame> {

  private static final int DEFAULT_CHUNK_SIZE = 8132;
  private static final int DEFAULT_MAX_LINE_LENGTH = 1024;

  enum State {
    SKIP_CONTROL_CHARACTERS,
    READ_HEADERS,
    READ_CONTENT,
    FINALIZE_FRAME_READ,
    BAD_FRAME,
    INVALID_CHUNK
  }

  private final int maxLineLength;
  private final int maxChunkSize;
  private final boolean validateHeaders;
  private int alreadyReadChunkSize;
  private LastStompContentSubframe lastContent;
  private long contentLength = -1;
  private int checkpoint = -1;

  private State state;

  public WebSocketStompFrameDecoder() {
    this(DEFAULT_MAX_LINE_LENGTH, DEFAULT_CHUNK_SIZE);
  }

  public WebSocketStompFrameDecoder(boolean validateHeaders) {
    this(DEFAULT_MAX_LINE_LENGTH, DEFAULT_CHUNK_SIZE, validateHeaders);
  }

  public WebSocketStompFrameDecoder(int maxLineLength, int maxChunkSize) {
    this(maxLineLength, maxChunkSize, false);
  }

  public WebSocketStompFrameDecoder(int maxLineLength, int maxChunkSize, boolean validateHeaders) {
    this.state = WebSocketStompFrameDecoder.State.SKIP_CONTROL_CHARACTERS;
    if (maxLineLength <= 0) {
      throw new IllegalArgumentException(
          "maxLineLength must be a positive integer: " +
              maxLineLength);
    }
    if (maxChunkSize <= 0) {
      throw new IllegalArgumentException(
          "maxChunkSize must be a positive integer: " +
              maxChunkSize);
    }
    this.maxChunkSize = maxChunkSize;
    this.maxLineLength = maxLineLength;
    this.validateHeaders = validateHeaders;
  }

  @Override
  protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame msg, List out)
      throws Exception {
    ByteBuf buffer = msg.content();

    doDecode(channelHandlerContext, buffer, out);
  }

  private void doDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    switch (state) {
      case SKIP_CONTROL_CHARACTERS:
        skipControlCharacters(in);
        checkpoint(WebSocketStompFrameDecoder.State.READ_HEADERS, in);
        // Fall through.
      case READ_HEADERS:
        StompCommand command = StompCommand.UNKNOWN;
        StompHeadersSubframe frame = null;
        try {
          command = readCommand(in);
          frame = new DefaultStompHeadersSubframe(command);
          checkpoint(readHeaders(in, frame.headers()), in);
          out.add(frame);
        } catch (Exception e) {
          if (frame == null) {
            frame = new DefaultStompHeadersSubframe(command);
          }
          frame.setDecoderResult(DecoderResult.failure(e));
          out.add(frame);
          checkpoint(WebSocketStompFrameDecoder.State.BAD_FRAME, in);
          return;
        }
        break;
      case BAD_FRAME:
        in.readableBytes();
        return;
    }
    try {
      switch (state) {
        case READ_CONTENT:
          int toRead = in.readableBytes();
          if (toRead == 0) {
            return;
          }
          if (toRead > maxChunkSize) {
            toRead = maxChunkSize;
          }
          if (contentLength >= 0) {
            int remainingLength = (int) (contentLength - alreadyReadChunkSize);
            if (toRead > remainingLength) {
              toRead = remainingLength;
            }
            ByteBuf chunkBuffer = readBytes(ctx.alloc(), in, toRead);
            if ((alreadyReadChunkSize += toRead) >= contentLength) {
              lastContent = new DefaultLastStompContentSubframe(chunkBuffer);
              checkpoint(WebSocketStompFrameDecoder.State.FINALIZE_FRAME_READ, in);
            } else {
              out.add(new DefaultStompContentSubframe(chunkBuffer));
              return;
            }
          } else {
            int nulIndex = indexOf(in, in.readerIndex(), in.writerIndex(), StompConstants.NUL);
            if (nulIndex == in.readerIndex()) {
              checkpoint(WebSocketStompFrameDecoder.State.FINALIZE_FRAME_READ, in);
            } else {
              if (nulIndex > 0) {
                toRead = nulIndex - in.readerIndex();
              } else {
                toRead = in.writerIndex() - in.readerIndex();
              }
              ByteBuf chunkBuffer = readBytes(ctx.alloc(), in, toRead);
              alreadyReadChunkSize += toRead;
              if (nulIndex > 0) {
                lastContent = new DefaultLastStompContentSubframe(chunkBuffer);
                checkpoint(WebSocketStompFrameDecoder.State.FINALIZE_FRAME_READ, in);
              } else {
                out.add(new DefaultStompContentSubframe(chunkBuffer));
                return;
              }
            }
          }
          // Fall through.
        case FINALIZE_FRAME_READ:
          skipNullCharacter(in);
          if (lastContent == null) {
            lastContent = LastStompContentSubframe.EMPTY_LAST_CONTENT;
          }
          out.add(lastContent);
          resetDecoder(in);
      }
    } catch (Exception e) {
      StompContentSubframe errorContent = new DefaultLastStompContentSubframe(Unpooled.EMPTY_BUFFER);
      errorContent.setDecoderResult(DecoderResult.failure(e));
      out.add(errorContent);
      checkpoint(WebSocketStompFrameDecoder.State.BAD_FRAME, in);
    }
  }

  private StompCommand readCommand(ByteBuf in) {
    String commandStr = readLine(in, 16);
    StompCommand command = null;
    try {
      command = StompCommand.valueOf(commandStr);
    } catch (IllegalArgumentException iae) {
      //do nothing
    }
    if (command == null) {
      commandStr = commandStr.toUpperCase(Locale.US);
      try {
        command = StompCommand.valueOf(commandStr);
      } catch (IllegalArgumentException iae) {
        //do nothing
      }
    }
    if (command == null) {
      throw new DecoderException("failed to read command from channel");
    }
    return command;
  }

  private WebSocketStompFrameDecoder.State readHeaders(ByteBuf buffer, StompHeaders headers) {
    AppendableCharSequence buf = new AppendableCharSequence(128);
    for (; ; ) {
      boolean headerRead = readHeader(headers, buf, buffer);
      if (!headerRead) {
        if (headers.contains(StompHeaders.CONTENT_LENGTH)) {
          contentLength = getContentLength(headers, 0);
          if (contentLength == 0) {
            return WebSocketStompFrameDecoder.State.FINALIZE_FRAME_READ;
          }
        }
        return WebSocketStompFrameDecoder.State.READ_CONTENT;
      }
    }
  }

  private static long getContentLength(StompHeaders headers, long defaultValue) {
    long contentLength = headers.getLong(StompHeaders.CONTENT_LENGTH, defaultValue);
    if (contentLength < 0) {
      throw new DecoderException(StompHeaders.CONTENT_LENGTH + " must be non-negative");
    }
    return contentLength;
  }

  private static void skipNullCharacter(ByteBuf buffer) {
    byte b = buffer.readByte();
    if (b != StompConstants.NUL) {
      throw new IllegalStateException("unexpected byte in buffer " + b + " while expecting NULL byte");
    }
  }

  private static void skipControlCharacters(ByteBuf buffer) {
    byte b;
    for (; ; ) {
      b = buffer.readByte();
      if (b != StompConstants.CR && b != StompConstants.LF) {
        buffer.readerIndex(buffer.readerIndex() - 1);
        break;
      }
    }
  }

  private String readLine(ByteBuf buffer, int initialBufferSize) {
    AppendableCharSequence buf = new AppendableCharSequence(initialBufferSize);
    int lineLength = 0;
    for (; ; ) {
      byte nextByte = buffer.readByte();
      if (nextByte == StompConstants.CR) {
        //do nothing
      } else if (nextByte == StompConstants.LF) {
        return buf.toString();
      } else {
        if (lineLength >= maxLineLength) {
          invalidLineLength();
        }
        lineLength++;
        buf.append((char) nextByte);
      }
    }
  }

  private boolean readHeader(StompHeaders headers, AppendableCharSequence buf, ByteBuf buffer) {
    buf.reset();
    int lineLength = 0;
    String key = null;
    boolean valid = false;
    for (; ; ) {
      byte nextByte = buffer.readByte();

      if (nextByte == StompConstants.COLON && key == null) {
        key = buf.toString();
        valid = true;
        buf.reset();
      } else if (nextByte == StompConstants.CR) {
        //do nothing
      } else if (nextByte == StompConstants.LF) {
        if (key == null && lineLength == 0) {
          return false;
        } else if (valid) {
          headers.add(key, buf.toString());
        } else if (validateHeaders) {
          invalidHeader(key, buf.toString());
        }
        return true;
      } else {
        if (lineLength >= maxLineLength) {
          invalidLineLength();
        }
        if (nextByte == StompConstants.COLON && key != null) {
          valid = false;
        }
        lineLength++;
        buf.append((char) nextByte);
      }
    }
  }

  private void invalidHeader(String key, String value) {
    String line = key != null ? key + ":" + value : value;
    throw new IllegalArgumentException("a header value or name contains a prohibited character ':'"
        + ", " + line);
  }

  private void invalidLineLength() {
    throw new TooLongFrameException("An STOMP line is larger than " + maxLineLength + " bytes.");
  }

  private void resetDecoder(ByteBuf in) {
    checkpoint(WebSocketStompFrameDecoder.State.SKIP_CONTROL_CHARACTERS, in);
    contentLength = -1;
    alreadyReadChunkSize = 0;
    lastContent = null;
  }

  /**
   * @param state state
   * @param buf buffer
   * Stores the internal cumulative buffer's reader position and updates
   * the current decoder state.
   */
  protected void checkpoint(WebSocketStompFrameDecoder.State state, ByteBuf buf) {
    checkpoint = buf.readerIndex();
    this.state = state;
  }
}
