package com.grupo06.sistemapedidos.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import io.jsonwebtoken.Claims;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private final Claims claims;

    public JwtAuthentication(Claims claims) {
        super(null);  // No necesitamos roles en este ejemplo simple
        this.claims = claims;
        setAuthenticated(true);  // Marcar como autenticado
    }

    @Override
    public Object getCredentials() {
        return null;  // No se necesitan credenciales para este caso
    }

    @Override
    public Object getPrincipal() {
        return claims.getSubject();  // El sujeto es el nombre de usuario almacenado en el token
    }
}
