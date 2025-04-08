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

    /**
     * Genera un token JWT para el usuario.
     *
     * @param username El nombre de usuario.
     * @return El token JWT generado.
     * @throws Exception Excepción en caso de error.
     */
    public String generateToken(String username) throws Exception {
        // Formatea la clave privada eliminando saltos de línea y espacios en blanco
        String formattedKey = privateKey.replaceAll("[\\n\\r\\s]", "");

        // Decodifica la clave privada desde Base64
        byte[] decoded = Base64.getMimeDecoder().decode(formattedKey);

        // Crea una instancia de KeyFactory para generar la clave RSA
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoded));

        // Crea el JWT utilizando la clave privada y la información del usuario
        return Jwts.builder()
                .setSubject(username) // Asigna el nombre de usuario como el sujeto del token
                .setIssuedAt(new Date()) // Fecha de emisión del token
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Fecha de expiración
                .signWith(key, SignatureAlgorithm.RS256) // Firma con la clave RSA y el algoritmo RS256
                .compact(); // Devuelve el token compactado
    }
}
