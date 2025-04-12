package com.grupo06.sistemapedidos.controller;

import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.enums.RoleEnum;
import com.grupo06.sistemapedidos.exception.UserError;
import com.grupo06.sistemapedidos.service.JwtTokenService;
import com.grupo06.sistemapedidos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final JwtTokenService jwtTokenService;
    private final UserService userService;

    public AuthController(JwtTokenService jwtTokenService, UserService userService) {
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
    }

    /**
     * Endpoint para generar un token JWT para el usuario con datos basados en el email.
     *
     * @param usuarioDTO DTO que contiene el email del usuario.
     * @return El token JWT generado para el usuario.
     * @throws UserError Si el usuario no se encuentra o si el rol no está asignado.
     */
    @PostMapping("/auth/token")
    @Operation(
            summary = "Generación de token JWT",
            description = "Genera un token JWT para el usuario basado en su email. Este token se utilizará para la autenticación en otros endpoints.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public String createToken(@RequestBody UsuarioDTO usuarioDTO) {
        String email = usuarioDTO.getEmail();
        // Buscar el usuario en la base de datos usando el email
        UsuarioDTO user = userService.getUserByEmail(email);

        if (user == null) {
            throw new UserError("Usuario no encontrado");
        }

        // Verificar el rol del usuario antes de generar el token
        RoleEnum userRole = user.getRol();
        if (userRole == null) {
            throw new UserError("El usuario no tiene un rol asignado");
        }

        // Generar el token JWT según el rol del usuario
        return jwtTokenService.generateTokenWithRole(email, userRole.name());
    }
}
