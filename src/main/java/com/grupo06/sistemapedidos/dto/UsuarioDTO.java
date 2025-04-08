package com.grupo06.sistemapedidos.dto;

import java.time.LocalDate;

import com.grupo06.sistemapedidos.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object para Usuarios
 * @Schema Permite configurar la documentación de Swagger para este DTO
 * @Getter, @Setter y @AllArgsConstructor, @NoArgsConstructor son anotaciones de Lombok para generar automáticamente 
 * los getters, setters y constructores con todos los argumentos, y sin argumentos
 * Para más infromacion sobre el dto, ver la documentacion de Swagger en: localhost:8080/api/swagger-ui.html
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private String name;
    private String email;
    private String password;
    private LocalDate signUpDate;
    private Integer totalSpend;
    private RoleEnum rol;
    private String token;

    public UsuarioDTO(String name, String email, LocalDate signUpDate) {
        this.name = name;
        this.email = email;
        this.signUpDate = signUpDate;
        this.totalSpend = 0;
        this.rol = RoleEnum.USER;
    }

    // Constructor para registro e inicio de sesión con rol y token
    public UsuarioDTO(String name, String email, String token) {
        this.name = name;
        this.email = email;
        this.token = token;
    }
}
