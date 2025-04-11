package com.grupo06.sistemapedidos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grupo06.sistemapedidos.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Data Transfer Object (DTO) para representar los datos de un Usuario.
 * Este DTO se utiliza para transferir información de usuario entre la capa de servicio y la capa de presentación.
 *
 * @Schema Permite configurar la documentación de Swagger para este DTO.
 * @Getter, @Setter, @AllArgsConstructor y @NoArgsConstructor son anotaciones de Lombok que generan automáticamente
 * los getters, setters, y constructores con todos los argumentos, y sin argumentos.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL) // Incluye solo los campos que no sean nulos al serializar a JSON
public class UsuarioDTO {

    private String name;
    private String email;
    private String password;
    private LocalDate signUpDate;
    private Integer totalSpend;
    @Getter
    private RoleEnum rol;
    private String token;

    /**
     * Constructor utilizado para la conversión desde entidad Usuario a UsuarioDTO.
     *
     * @param name       El nombre del usuario.
     * @param email      El correo electrónico del usuario.
     * @param password   La contraseña del usuario.
     * @param signUpDate La fecha de registro del usuario.
     * @param rol        El rol asignado al usuario (RoleEnum).
     */
    public UsuarioDTO(String name, String email, String password, LocalDate signUpDate, RoleEnum rol) {
        this.name = name;
        this.email = email;
        this.signUpDate = signUpDate;
        this.password = password;
        this.totalSpend = 0; // El gasto inicial es 0
        this.rol = rol != null ? rol : RoleEnum.GUEST;  // Si el rol es nulo, se asigna el rol por defecto GUEST
    }

    /**
     * Constructor para crear un DTO de usuario en procesos de registro e inicio de sesión.
     *
     * @param name  El nombre del usuario.
     * @param email El correo electrónico del usuario.
     */
    public UsuarioDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Constructor para asignar un rol a un usuario basándose en el nombre, correo y rol en formato String.
     *
     * @param name  El nombre del usuario.
     * @param email El correo electrónico del usuario.
     * @param role  El rol del usuario en formato String, que será convertido a un valor de RoleEnum.
     */
    public UsuarioDTO(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.rol = RoleEnum.valueOf(role); // Convierte el valor String del rol a un valor de RoleEnum
    }
}
