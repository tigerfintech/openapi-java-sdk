package com.tigerbrokers.stock.openapi.client.auth;

/**
 * How a request authenticates.
 *
 * <p>The two are mutually exclusive: {@link #SIGNATURE} puts {@code tiger_id} and an RSA
 * signature in the request body, {@link #OAUTH2} puts a Bearer token in the
 * {@code Authorization} header and puts <b>no signature field at all</b> in the body.
 *
 * <p>Sending both "just in case" does not work: the gateway takes the signature branch as
 * soon as it sees {@code sign}, so the Bearer token is ignored and then misread as an HK
 * license token, producing an error that has nothing to do with the auth method.
 */
public enum AuthenticationType {

  /** The legacy way: tigerId + RSA signature with a private key. */
  SIGNATURE,

  /** OAuth2: {@code Authorization: Bearer <access_token>}. */
  OAUTH2
}
