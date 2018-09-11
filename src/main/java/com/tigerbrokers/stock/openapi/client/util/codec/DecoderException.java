package com.tigerbrokers.stock.openapi.client.util.codec;

/**
 * Thrown when a Decoder has encountered a failure condition during a decode.
 *
 * @author Apache Software Foundation
 * @version $Id: DecoderException.java,v 1.9 2004/02/29 04:08:31 tobrien Exp $
 */
public class DecoderException extends Exception {

  private static final long serialVersionUID = -8524444543640582985L;

  /**
   * Creates a DecoderException
   *
   * @param pMessage A message with meaning to a human
   */
  public DecoderException(String pMessage) {
    super(pMessage);
  }
}

