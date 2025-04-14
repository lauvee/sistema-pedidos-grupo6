package com.grupo06.sistemapedidos.dto;

import java.util.List;
import com.grupo06.sistemapedidos.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un rol en el sistema en la base de datos, osea la entidad.
 * Un rol tiene un nombre y una descripción.
 * Esta clase es parte del modelo de datos y se utiliza para mapear la tabla "ROLES" en la base de datos.
 * Contiene anotaciones de JPA para la persistencia y validaciones de datos.
 * 
 * @Data es una anotación de Lombok que genera automáticamente
 */
@Data
@NoArgsConstructor
@Schema(description = "DTO para representar un rol")
public class RolesDTO {
    @NotNull(message = "El nombre del rol no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 20)
    @Schema(description = "Nombre del rol", example = "ADMIN")
    private RoleEnum name;

    @NotEmpty(message = "La descripción no puede estar vacía")
    @Column(name = "description", nullable = false, length = 255)
    @Schema(description = "Descripción del rol", example = "Rol de administrador con todos los permisos")
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "Lista de usuarios asociados a este rol", example = "[1, 2, 3]")
    private List<Integer> users;

    public RolesDTO(RoleEnum name, String description) {
        this.name = name;
        this.description = description;
    }
}