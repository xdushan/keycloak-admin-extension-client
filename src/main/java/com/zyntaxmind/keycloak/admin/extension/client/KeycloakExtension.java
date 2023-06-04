/*******************************************************************************
 *    Copyright 2023  the original author.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
/**
 * 
 */
package com.zyntaxmind.keycloak.admin.extension.client;

import static org.keycloak.OAuth2Constants.PASSWORD;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.keycloak.admin.client.Config;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.BearerAuthFilter;
import org.keycloak.admin.client.token.TokenManager;
import com.zyntaxmind.keycloak.admin.extension.client.resource.AdminRealmExtensionResource;

/**
 * @author dush
 *
 */
public class KeycloakExtension {

  private final Config config;
  
  private final WebTarget target;
  
  private final Client client;
  
  private final TokenManager tokenManager;
  
  private final String authToken;

  public KeycloakExtension(String serverUrl, String realm, String username, String password, String clientId, String clientSecret, String grantType, Client resteasyClient, String authtoken, String scope) {
    this.config = new Config(serverUrl, realm, username, password, clientId, clientSecret, grantType, scope);
    client = resteasyClient != null ? resteasyClient : newRestEasyClient(null, null, false);
    authToken = authtoken;
    tokenManager = authtoken == null ? new TokenManager(config, client) : null;
    this.target = client.target(config.getServerUrl().concat("/admin/realms/").concat(realm));
    this.target.register(newAuthFilter());
  }

  private static Client newRestEasyClient(Object customJacksonProvider, SSLContext sslContext,
      boolean disableTrustManager) {
    return Keycloak.getClientProvider().newRestEasyClient(customJacksonProvider, sslContext, disableTrustManager);
  }

  private BearerAuthFilter newAuthFilter() {
    return authToken != null ? new BearerAuthFilter(authToken) : new BearerAuthFilter(tokenManager);
  }
  
  public static KeycloakExtension getInstance(String serverUrl, String realm, String username, String password, String clientId, String clientSecret, SSLContext sslContext, Object customJacksonProvider, boolean disableTrustManager, String authToken, String scope) {
    return new KeycloakExtension(serverUrl, realm, username, password, clientId, clientSecret, PASSWORD, newRestEasyClient(customJacksonProvider, sslContext, disableTrustManager), authToken, scope);
  }

  public static KeycloakExtension getInstance(String serverUrl, String realm, String username, String password, String clientId, String clientSecret, SSLContext sslContext, Object customJacksonProvider, boolean disableTrustManager, String authToken) {
    return new KeycloakExtension(serverUrl, realm, username, password, clientId, clientSecret, PASSWORD, newRestEasyClient(customJacksonProvider, sslContext, disableTrustManager), authToken, null);
  }

  public static KeycloakExtension getInstance(String serverUrl, String realm, String username, String password, String clientId, String clientSecret) {
    return getInstance(serverUrl, realm, username, password, clientId, clientSecret, null, null, false, null);
  }

  public static KeycloakExtension getInstance(String serverUrl, String realm, String username, String password, String clientId, String clientSecret, SSLContext sslContext) {
    return getInstance(serverUrl, realm, username, password, clientId, clientSecret, sslContext, null, false, null);
  }

  public static KeycloakExtension getInstance(String serverUrl, String realm, String username, String password, String clientId, String clientSecret, SSLContext sslContext, Object customJacksonProvider) {
    return getInstance(serverUrl, realm, username, password, clientId, clientSecret, sslContext, customJacksonProvider, false, null);
  }

  public static KeycloakExtension getInstance(String serverUrl, String realm, String username, String password, String clientId) {
    return getInstance(serverUrl, realm, username, password, clientId, null, null, null, false, null);
  }

  public static KeycloakExtension getInstance(String serverUrl, String realm, String username, String password, String clientId, SSLContext sslContext) {
    return getInstance(serverUrl, realm, username, password, clientId, null, sslContext, null, false, null);
  }

  public static KeycloakExtension getInstance(String serverUrl, String realm, String clientId, String authToken) {
    return getInstance(serverUrl, realm, null, null, clientId, null, null, null, false, authToken);
  }

  public static KeycloakExtension getInstance(String serverUrl, String realm, String clientId, String authToken, SSLContext sllSslContext) {
    return getInstance(serverUrl, realm, null, null, clientId, null, sllSslContext, null, false, authToken);
  }
  
  public AdminRealmExtensionResource realmExtenstion() {
    return Keycloak.getClientProvider().targetProxy(target, AdminRealmExtensionResource.class);
  }
}
