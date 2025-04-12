package com.grupo06.sistemapedidos.model;

import java.util.List;

import com.grupo06.sistemapedidos.enums.RoleEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Clase que representa un rol en el sistema en la base de datos, osea la entidad.
 * Un rol tiene un nombre y una descripción.
 * Esta clase es parte del modelo de datos y se utiliza para mapear la tabla "ROLES" en la base de datos.
 * Contiene anotaciones de JPA para la persistencia y validaciones de datos.
 * 
 * @Schema es una anotación de Swagger para documentar la API
 * @Data es una anotación de Lombok que genera automáticamente
 */
@Schema(description = "Clase que representa un rol en el sistema.")
@Entity
@Table(name = "ROLES", schema = "public")
@Data
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRol", nullable = false, unique = true)
    private Integer id;

    @NotNull(message = "El nombre del rol no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 20)
    private RoleEnum name;

    @NotEmpty(message = "La descripción no puede estar vacía")
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> users;

}