package com.grupo06.sistemapedidos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que representa la entidad Usuario en la base de datos.
 * Contiene información sobre el usuario, incluyendo su nombre,
 * correo electrónico, contraseña, fecha de registro,
 * total gastado y su rol.
 * 
 * @Schema es una anotación de Swagger para documentar la API
 * @Data es una anotación de Lombok que genera automáticamente
 */
@Schema(description = "Clase que representa la entidad Usuario en la base de datos.")
@Entity
@Table(name = "USUARIO", schema = "public")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser", unique = true, nullable = false)
    private Integer id;

    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Size(min = 2, max = 50, message = "{user.name.size}")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "{user.email}")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Column(name = "password", nullable = false)
    private String password;


    @NotNull(message = "{field.null}")
    @Column(name = "signUpDate", nullable = false)
    private LocalDate signUpDate;

    @NotNull(message = "{field.null}")
    @Column(name = "totalspend", nullable = false)
    private Integer totalSpend = 0;


    @NotNull(message = "{field.null}")
    @ManyToOne
    @JoinColumn(name = "roleFK", referencedColumnName = "idRol", nullable = false)
    private Roles role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();
}
