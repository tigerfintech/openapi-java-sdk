package com.tigerbrokers.stock.openapi.client.auth;

/**
 * Supported request authentication methods.
 *
 * <p>Signature authentication adds {@code tiger_id} and {@code sign} to the request body.
 * OAuth2 authentication sends a Bearer token in the {@code Authorization} header and omits the
 * signature fields.</p>
 */
public enum AuthenticationType {

  /** RSA signature authentication using tigerId and a private key. */
  SIGNATURE,

  /** OAuth2: {@code Authorization: Bearer <access_token>}. */
  OAUTH2
}
