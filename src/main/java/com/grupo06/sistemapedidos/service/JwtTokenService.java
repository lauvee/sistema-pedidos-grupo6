package com.grupo06.sistemapedidos.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtTokenService {

    @Value("${jwt.private.key}")
    private String privateKey;

    @Value("${jwt.expiration.time}")
    private long expirationTime;

    public String generateToken(String username) throws Exception {
        String formattedKey = privateKey.replaceAll("[\\n\\r\\s]", "");

        // Usar Base64 MIME decoder
        byte[] decoded = Base64.getMimeDecoder().decode(formattedKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoded));

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.RS256)
                .compact();
    }

}
