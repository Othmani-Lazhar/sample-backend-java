package com.virgilsecurity.demo.server.service;

import com.virgilsecurity.sdk.crypto.exceptions.CryptoException;
import com.virgilsecurity.sdk.jwt.Jwt;
import com.virgilsecurity.sdk.jwt.JwtGenerator;
import com.virgilsecurity.sdk.utils.StringUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private Map<String, String> authTokens;

  @Autowired
  JwtGenerator jwtGenerator;

  public AuthenticationService() {
    this.authTokens = new ConcurrentHashMap<>();
  }

  public String login(String identity) {
    String authToken = String.format("%s-%s", identity, UUID.randomUUID().toString());
    this.authTokens.put(authToken, identity);
    return authToken;
  }

  public String getIdentity(String authToken) {
    if (StringUtils.isBlank(authToken)) {
      return null;
    }
    String identity = this.authTokens.get(authToken);
    return identity;
  }

  public Jwt generateVirgilToken(String identity) throws CryptoException {
    return jwtGenerator.generateToken(identity);
  }

}
