package com.grupo06.sistemapedidos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grupo06.sistemapedidos.enums.RoleEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Data Transfer Object (DTO) para representar los datos de un Usuario.
 * Este DTO se utiliza para transferir información de usuario entre la capa de servicio y la capa de presentación.
 * Se omiten los campos id, password
 * 
 * @Getter, @Setter, @AllArgsConstructor y @NoArgsConstructor son anotaciones de Lombok que generan automáticamente
 * los getters, setters, y constructores con todos los argumentos, y sin argumentos.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL) // Incluye solo los campos que no sean nulos al serializar a JSON
@Schema(description = "DTO para representar un usuario")
public class UsuarioDTO {
    @Schema(description =  "Nombre del usuario", example = "Miguel Angel Duran")
    private String name;
    @Schema(description = "Email del usuario", example = "miguelangelduran@gmial.com")
    private String email;
    @Schema(description = "Contraseña del usuario", example = "12345678")
    private String password;
    @Schema(description = "Fecha de registro del usuario", example = "2023-10-01")
    private LocalDate signUpDate;
    @Schema(description = "Total gastado por el usuario", example = "1000")
    private Integer totalSpend;
    @Schema(description = "Rol del usuario", example = "ADMIN")
    private RoleEnum rol;
    @Schema(hidden = true)
    private String token;

    public UsuarioDTO(String name, String email, String password, LocalDate signUpDate, RoleEnum rol) {
        this.name = name;
        this.email = email;
        this.signUpDate = signUpDate;
        this.password = password;
        this.totalSpend = 0; // El gasto inicial es 0
        this.rol = rol != null ? rol : RoleEnum.GUEST;  // Si el rol es nulo, se asigna el rol por defecto GUEST
    }

    /**
     * Constructor para asignar un rol a un usuario basándose en el nombre, correo y rol en formato String.
     *
     * @param name  El nombre del usuario.
     * @param email El correo electrónico del usuario.
     * @param role  El rol del usuario en formato String, que será convertido a un valor de RoleEnum.
     */
    public UsuarioDTO(String name, String email, String role, String token) {
        this.name = name;
        this.email = email;
        this.rol = RoleEnum.valueOf(role); // Convierte el valor String del rol a un valor de RoleEnum
        this.token = token; // Asigna el token al usuario
    }
}
