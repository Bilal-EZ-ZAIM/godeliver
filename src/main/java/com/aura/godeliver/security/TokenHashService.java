package com.aura.godeliver.security;

public interface TokenHashService {

    String hash(String token);
}