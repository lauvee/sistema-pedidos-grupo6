package com.grupo06.sistemapedidos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import io.swagger.v3.oas.annotations.Hidden;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Clase que representa la entidad Usuario en la base de datos.
 * Contiene información sobre el usuario, incluyendo su nombre,
 * correo electrónico, contraseña, fecha de registro,
 * total gastado y su rol.
 * 
 * @Hidden es una anotación de Swagger que oculta la clase de la documentación
 * @Entity indica que esta clase es una entidad JPA
 * @Table especifica el nombre de la tabla en la base de datos
 * @Data es una anotación de Lombok que genera automáticamente
 */
@Hidden
@Entity
@Table(name = "USUARIO", schema = "public")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser", unique = true, nullable = false)
    private Integer id;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre tiene que estar entre 2 y 50 caracteres")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull(message = "El email no puede ser nulo")
    @NotEmpty(message = "El email no puede estar vacío")
    @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "{user.email}")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotNull(message = "La contraseña no puede ser nula")
    @NotEmpty(message = "La contraseña no puede estar vacía")
    @Size(min = 5, max = 100, message = "La constraseña tiene que estar entre 5 y 20 caracteres")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "La fecha no puede ser nula")
    @Column(name = "signUpDate", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate signUpDate;

    @NotNull(message = "El total gastado no puede ser nulo")
    @Column(name = "totalSpent", nullable = false)
    private Integer totalSpent = 0;

    @NotNull(message = "El rol no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "roleFK", referencedColumnName = "idRol", nullable = false)
    @ToString.Exclude 
    private Roles role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();
}
