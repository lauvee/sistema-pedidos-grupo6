package com.grupo06.sistemapedidos.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

/**
 * Filtro de autenticación JWT para validar la autenticación de usuarios a través de un token JWT.
 * Extiende de 'OncePerRequestFilter' de Spring Security, asegurando que el filtro solo se ejecute una vez por cada solicitud.
 *
 * Este filtro se encarga de extraer y validar el token JWT enviado en el encabezado "Authorization" de las solicitudes HTTP,
 * estableciendo la autenticación del usuario en el contexto de seguridad de Spring si el token es válido.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String jwtSecret;

    /**
     * Constructor que recibe la clave secreta utilizada para la validación del token JWT.
     *
     * @param jwtSecret La clave secreta utilizada para firmar y validar el token JWT.
     */
    public JwtAuthenticationFilter(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    /**
     * Método principal que procesa la solicitud HTTP para verificar y validar el token JWT.
     * Si el token es válido, se establece la autenticación en el contexto de seguridad de Spring.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param filterChain La cadena de filtros de Spring Security.
     * @throws ServletException Si ocurre un error al procesar la solicitud.
     * @throws IOException Si ocurre un error al escribir la respuesta.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtener el encabezado "Authorization" de la solicitud.
        String header = request.getHeader("Authorization");

        // Verificar si el encabezado contiene el prefijo "Bearer ".
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);  // Extraer el token eliminando el prefijo "Bearer ".

            try {
                // Crear la clave secreta a partir de la clave proporcionada (jwtSecret).
                SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());

                // Parsear el token JWT utilizando la clave secreta y obtener los claims.
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                Authentication authentication = new JwtAuthentication(claims.getSubject());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // Si ocurre un error durante la validación del token, responder con un estado 401 (No autorizado).
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
