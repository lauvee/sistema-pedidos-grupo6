package com.grupo06.sistemapedidos.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Servicio para generar y gestionar tokens JWT.
 *
 * Este servicio se encarga de la creación de tokens JWT firmados utilizando una clave secreta y con una fecha de expiración configurada.
 * El token también contiene el correo electrónico del usuario y su rol.
 */
@Service
public class JwtTokenService {

    @Value("${jwt.secret.key}")
    private String jwtSecret;
    @Value("${jwt.expiration.time}")
    private long expirationTime;

    /**
     * Método para generar un token JWT firmado con el correo electrónico y rol del usuario.
     *
     * Este token tiene una fecha de emisión y una fecha de expiración configurada en la aplicación.
     *
     * @param email Correo electrónico del usuario, que será utilizado como el "subject" del token.
     * @param role El rol del usuario, que será guardado como un claim adicional en el token.
     * @return El token JWT generado.
     */
    public String generateTokenWithRole(String email, String role) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        // Construir el token JWT con los parámetros correspondientes:
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
