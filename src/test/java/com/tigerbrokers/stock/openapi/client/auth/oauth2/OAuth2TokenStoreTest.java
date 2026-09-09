package com.tigerbrokers.stock.openapi.client.auth.oauth2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * OAuth2 local persistence.
 *
 * <p>Covers test-plan cases I-01..I-08, I-10, I-11, I-15, L-13. Filesystem only, no network.</p>
 */
public class OAuth2TokenStoreTest {

  private static final Charset UTF_8 = Charset.forName("UTF-8");
  private static final String ISSUER = "https://as.example.com";
  private static final String CLIENT_ID = "client-abc";

  private Path home;
  private OAuth2TokenStore store;

  @Before
  public void setUp() throws IOException {
    home = Files.createTempDirectory("tigeropen-oauth2-store-");
    store = new OAuth2TokenStore(home.toString());
  }

  @After
  public void tearDown() {
    deleteRecursively(home.toFile());
  }

  private static OAuth2Token aToken() {
    return aToken("AT", "RT");
  }

  private static OAuth2Token aToken(String accessToken, String refreshToken) {
    OAuth2Token token = new OAuth2Token();
    token.setIssuer(ISSUER);
    token.setClientId(CLIENT_ID);
    token.setAccessToken(accessToken);
    token.setRefreshToken(refreshToken);
    token.setScope("api.quote:read");
    token.setExpiresAt(System.currentTimeMillis() + 3600_000L);
    return token;
  }

  private Path tokenFile() {
    return home.resolve("tokens").resolve(CLIENT_ID + ".json");
  }

  private static boolean posixSupported(Path path) {
    return path.getFileSystem().supportedFileAttributeViews().contains("posix");
  }

  private static void deleteRecursively(File file) {
    File[] children = file.listFiles();
    if (children != null) {
      for (File child : children) {
        deleteRecursively(child);
      }
    }
    file.delete();
  }

  // ------------------------------------------------------------- round trip

  @Test
  public void saveThenLoad() {
    store.saveToken(aToken());
    OAuth2Token loaded = store.loadToken(CLIENT_ID, ISSUER);
    assertEquals("AT", loaded.getAccessToken());
    assertEquals("RT", loaded.getRefreshToken());
    assertEquals("api.quote:read", loaded.getScope());
  }

  @Test
  public void missingFileIsNotAnError() {
    assertNull(store.loadToken("nobody", ISSUER));
  }

  /** I-03: the field set is a cross-language contract. */
  @Test
  public void onDiskFormat() throws IOException {
    store.saveToken(aToken());
    JSONObject json = JSON.parseObject(new String(Files.readAllBytes(tokenFile()), UTF_8));

    Set<String> expected = new HashSet<>();
    expected.add("schema_version");
    expected.add("issuer");
    expected.add("client_id");
    expected.add("access_token");
    expected.add("refresh_token");
    expected.add("token_type");
    expected.add("scope");
    expected.add("expires_at");
    assertEquals(expected, json.keySet());

    assertEquals(1, json.getIntValue("schema_version"));
    assertEquals("Bearer", json.getString("token_type"));
  }

  /** B-09: an absolute millisecond timestamp, not expires_in. */
  @Test
  public void expiresAtIsEpochMillis() throws IOException {
    store.saveToken(aToken());
    JSONObject json = JSON.parseObject(new String(Files.readAllBytes(tokenFile()), UTF_8));
    assertTrue(json.getLongValue("expires_at") > 1_600_000_000_000L);
  }

  @Test
  public void deleteToken() {
    store.saveToken(aToken());
    store.deleteToken(CLIENT_ID);
    assertNull(store.loadToken(CLIENT_ID, ISSUER));
  }

  @Test
  public void saveWithoutClientIdIsRejected() {
    OAuth2Token token = new OAuth2Token();
    token.setIssuer(ISSUER);
    try {
      store.saveToken(token);
      fail("expected IllegalArgumentException");
    } catch (IllegalArgumentException expected) {
      // clientId is the filename; without it there is nowhere to write
    }
  }

  // ------------------------------------------------------------ permissions

  /** I-01: the token file is a credential at rest. */
  @Test
  public void tokenFileIs0600() throws IOException {
    store.saveToken(aToken());
    if (!posixSupported(tokenFile())) {
      return;
    }
    Set<PosixFilePermission> perms = Files.getPosixFilePermissions(tokenFile());
    assertEquals(2, perms.size());
    assertTrue(perms.contains(PosixFilePermission.OWNER_READ));
    assertTrue(perms.contains(PosixFilePermission.OWNER_WRITE));
  }

  @Test
  public void directoriesAre0700() throws IOException {
    store.saveToken(aToken());
    store.saveClientRegistration(aRegistration());
    if (!posixSupported(home)) {
      return;
    }
    for (String sub : new String[] {"tokens", "clients"}) {
      Set<PosixFilePermission> perms = Files.getPosixFilePermissions(home.resolve(sub));
      assertEquals(sub, 3, perms.size());
      assertTrue(sub, perms.contains(PosixFilePermission.OWNER_EXECUTE));
    }
  }

  @Test
  public void permissionsSurviveOverwrite() throws IOException {
    store.saveToken(aToken());
    store.saveToken(aToken("AT2", "RT2"));
    if (!posixSupported(tokenFile())) {
      return;
    }
    assertEquals(2, Files.getPosixFilePermissions(tokenFile()).size());
  }

  // ----------------------------------------------------------- atomic write

  /** I-02: a crash mid-write must not leave a truncated JSON file. */
  @Test
  public void noTempFilesLeftBehind() throws IOException {
    store.saveToken(aToken());
    File[] leftovers = home.resolve("tokens").toFile().listFiles();
    assertNotNull(leftovers);
    for (File file : leftovers) {
      assertFalse(file.getName(), file.getName().startsWith(".tmp-"));
    }
  }

  /** An unwritable target reports STORAGE_FAILED rather than leaving a half-written file. */
  @Test
  public void unwritableTargetIsReported() throws IOException {
    // Occupy the "tokens" path with a regular file so the directory cannot be created
    Files.write(home.resolve("tokens"), "not a directory".getBytes(UTF_8));
    try {
      store.saveToken(aToken());
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.STORAGE_FAILED, e.getCategory());
    }
  }

  // --------------------------------------------------------- issuer binding

  /** I-04: prevents sending a staging token to production. */
  @Test
  public void mismatchedIssuerIsASilentCacheMiss() {
    store.saveToken(aToken());
    assertNull(store.loadToken(CLIENT_ID, "https://other.example.com"));
  }

  @Test
  public void noExpectedIssuerSkipsTheCheck() {
    store.saveToken(aToken());
    assertNotNull(store.loadToken(CLIENT_ID, null));
  }

  // ------------------------------------------------------------- corruption

  /** I-05: guessing at an unknown format is worse than refusing. */
  @Test
  public void unknownSchemaVersionFailsLoudly() throws IOException {
    store.saveToken(aToken());
    JSONObject json = JSON.parseObject(new String(Files.readAllBytes(tokenFile()), UTF_8));
    json.put("schema_version", 99);
    Files.write(tokenFile(), json.toJSONString().getBytes(UTF_8));

    try {
      store.loadToken(CLIENT_ID, ISSUER);
      fail("expected OAuth2Exception");
    } catch (OAuth2Exception e) {
      assertEquals(OAuth2Exception.Category.STORAGE_FAILED, e.getCategory());
    }
  }

  /** I-06: re-authorizing beats crashing. */
  @Test
  public void malformedJsonIsTreatedAsAbsent() throws IOException {
    Files.createDirectories(tokenFile().getParent());
    Files.write(tokenFile(), "{not json at all".getBytes(UTF_8));
    assertNull(store.loadToken(CLIENT_ID, ISSUER));
  }

  @Test
  public void emptyFileIsTreatedAsAbsent() throws IOException {
    Files.createDirectories(tokenFile().getParent());
    Files.write(tokenFile(), new byte[0]);
    assertNull(store.loadToken(CLIENT_ID, ISSUER));
  }

  /** I-07: readable, but the caller must see there is nothing to refresh with. */
  @Test
  public void missingRefreshTokenField() throws IOException {
    store.saveToken(aToken());
    JSONObject json = JSON.parseObject(new String(Files.readAllBytes(tokenFile()), UTF_8));
    json.remove("refresh_token");
    Files.write(tokenFile(), json.toJSONString().getBytes(UTF_8));

    OAuth2Token loaded = store.loadToken(CLIENT_ID, ISSUER);
    assertNotNull(loaded);
    assertFalse(loaded.hasRefreshToken());
  }

  @Test
  public void missingExpiresAtDefaultsToExpired() throws IOException {
    store.saveToken(aToken());
    JSONObject json = JSON.parseObject(new String(Files.readAllBytes(tokenFile()), UTF_8));
    json.remove("expires_at");
    Files.write(tokenFile(), json.toJSONString().getBytes(UTF_8));

    OAuth2Token loaded = store.loadToken(CLIENT_ID, ISSUER);
    assertEquals(0L, loaded.getExpiresAt());
    assertTrue(loaded.needsRefresh(0));
  }

  // ---------------------------------------------------- client registration

  private static OAuth2ClientRegistration aRegistration() {
    OAuth2ClientRegistration reg = new OAuth2ClientRegistration();
    reg.setIssuer(ISSUER);
    reg.setClientId(CLIENT_ID);
    reg.setRegisteredScopes("api.quote:read");
    return reg;
  }

  @Test
  public void registrationRoundTrip() {
    store.saveClientRegistration(aRegistration());
    OAuth2ClientRegistration loaded = store.loadClientRegistration(ISSUER);
    assertEquals(CLIENT_ID, loaded.getClientId());
    assertEquals("api.quote:read", loaded.getRegisteredScopes());
    assertEquals(CLIENT_ID, store.loadClientId(ISSUER));
  }

  /** It is looked up before any client_id is known, so the issuer is the key. */
  @Test
  public void registrationIsKeyedByIssuer() {
    store.saveClientRegistration(aRegistration());
    assertNull(store.loadClientRegistration("https://other.example.com"));
  }

  @Test
  public void registrationFileNameIsTheIssuerHash() {
    store.saveClientRegistration(aRegistration());
    String[] names = home.resolve("clients").toFile().list();
    assertNotNull(names);
    assertEquals(1, names.length);
    assertEquals(OAuth2TokenStore.hash(ISSUER) + ".json", names[0]);
  }

  /** I-15 / D-14: otherwise every logout piles up a new client on the server. */
  @Test
  public void deletingTheTokenKeepsTheRegistration() {
    store.saveToken(aToken());
    store.saveClientRegistration(aRegistration());

    store.deleteToken(CLIENT_ID);

    assertNull(store.loadToken(CLIENT_ID, ISSUER));
    assertNotNull(store.loadClientRegistration(ISSUER));
  }

  @Test
  public void deleteRegistration() {
    store.saveClientRegistration(aRegistration());
    store.deleteClientRegistration(ISSUER);
    assertNull(store.loadClientRegistration(ISSUER));
  }

  @Test
  public void malformedRegistrationIsTreatedAsAbsent() throws IOException {
    store.saveClientRegistration(aRegistration());
    Path file = home.resolve("clients").resolve(OAuth2TokenStore.hash(ISSUER) + ".json");
    Files.write(file, "{broken".getBytes(UTF_8));
    assertNull(store.loadClientRegistration(ISSUER));
  }

  // ------------------------------------------------------- name derivation

  @Test
  public void issuerHashIsStableAndShort() {
    String hash = OAuth2TokenStore.hash(ISSUER);
    assertEquals(32, hash.length());
    assertEquals(hash, OAuth2TokenStore.hash(ISSUER));
    assertFalse(hash.equals(OAuth2TokenStore.hash(ISSUER + "/")));
  }

  /**
   * A clientId comes from the server, so it cannot be trusted as a path component.
   */
  @Test
  public void clientIdCannotEscapeTheTokenDirectory() {
    OAuth2Token token = aToken();
    token.setClientId("../../etc/passwd");
    store.saveToken(token);

    File[] outside = home.getParent().toFile().listFiles();
    assertNotNull(outside);
    for (File file : outside) {
      assertFalse(file.getName(), "passwd".equals(file.getName()));
    }
    File[] written = home.resolve("tokens").toFile().listFiles();
    assertEquals(1, written.length);
    assertFalse(written[0].getName(), written[0].getName().contains(".."));
  }

  // ------------------------------------------------------- home resolution

  @Test
  public void explicitHomeWins() {
    assertEquals(home, new OAuth2TokenStore(home.toString()).getHome());
  }

  /** L-13: separate from the legacy license-token store, which is cwd-relative. */
  @Test
  public void defaultHomeIsUnderTheUserHome() {
    String envHome = System.getenv("TIGEROPEN_HOME");
    if (envHome != null && !envHome.isEmpty()) {
      return;   // the environment overrides the default; nothing to assert here
    }
    Path expected = new File(System.getProperty("user.home")
        + File.separator + ".tiger" + File.separator + "openapi").toPath();
    assertEquals(expected, new OAuth2TokenStore().getHome());
  }
}
