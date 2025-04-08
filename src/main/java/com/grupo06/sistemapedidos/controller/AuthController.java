package com.grupo06.sistemapedidos.controller;

import com.grupo06.sistemapedidos.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtTokenService jwtTokenService;

    /**
     * Endpoint que genera un token JWT para el usuario con datos dummy.
     * @return El token JWT generado para el usuario dummy.
     */
    @GetMapping("/auth/token")
    public String createToken() throws Exception {
        // Datos dummy para el nombre de usuario
        String username = "dummyUser";

        // Generar el token JWT usando el servicio
        return jwtTokenService.generateToken(username);
    }
}
