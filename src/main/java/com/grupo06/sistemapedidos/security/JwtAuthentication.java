package com.grupo06.sistemapedidos.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Clase personalizada que representa la autenticación de un usuario a través de un token JWT.
 * Extiende de 'AbstractAuthenticationToken' de Spring Security para integrar la autenticación en el contexto de seguridad.
 *
 * Esta clase es utilizada en el proceso de autenticación JWT, donde el nombre de usuario se obtiene del token.
 *
 */
public class JwtAuthentication extends AbstractAuthenticationToken {

    private final String username;

    /**
     * Constructor que inicializa la autenticación con el nombre de usuario extraído del token JWT.
     *
     * @param username El nombre de usuario obtenido del token JWT.
     */
    public JwtAuthentication(String username) {
        super(null);  // En este caso, no se proporcionan autoridades ya que el token no contiene roles.
        this.username = username;
        setAuthenticated(true);  // Se marca la autenticación como válida desde que el token ha sido procesado.
    }

   /**
     * Constructor completo que inicializa la autenticación con el nombre de usuario y sus roles.
     *
     * @param username El nombre de usuario obtenido del token JWT.
     * @param roles Lista de roles/autoridades del usuario.
     */
    public JwtAuthentication(String username, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        setAuthenticated(true);
    }
    
    /**
     * Constructor alternativo que acepta roles como strings y los convierte a autoridades.
     *
     * @param username El nombre de usuario obtenido del token JWT.
     * @param roles Lista de roles como strings (ej: "ADMIN", "USER").
     */
    public JwtAuthentication(String username, List<String> roles) {
        this(username, 
             roles.stream()
                 .map(SimpleGrantedAuthority::new)
                 .collect(Collectors.toList())
        );
    }

    /**
     * Método que devuelve las credenciales del usuario. En este caso, no se utiliza ninguna credencial
     * específica ya que el token es suficiente para la autenticación.
     *
     * @return null, ya que las credenciales no se almacenan explícitamente en este caso.
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Método que devuelve el principal de la autenticación, que en este caso es el nombre de usuario.
     *
     * @return El nombre de usuario que fue extraído del token JWT.
     */
    @Override
    public Object getPrincipal() {
        return username;
    }
}
