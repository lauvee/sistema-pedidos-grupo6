package com.grupo06.sistemapedidos.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa la entidad Usuario en la base de datos.
 * Contiene información sobre el usuario, incluyendo su nombre, 
 * correo electrónico, contraseña, fecha de registro,
 * total gastado y su rol.
 * 
 * @Schema Permite configurar la documentación de Swagger para esta entidad
 * @Getter, @Setter y @NoArgsConstructor son anotaciones de Lombok para generar automáticamente 
 * los getters, setters y constructores sin argumentos
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "USUARIO",  schema = "public")
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
    @Column(name = "password", nullable = false, length = 255)
    private String password;

   
    @NotNull(message = "{field.null}")
    @Column(name = "signUpDate", nullable = false)
    private LocalDate signUpDate;

    @NotNull(message = "{field.null}")
    @Column(name = "totalSpent", nullable = false)
    private Integer totalSpent;

    @NotNull(message = "{field.null}")
    @ManyToOne
    @JoinColumn(name = "roleFK", referencedColumnName = "idRol", nullable = false)
    private Roles role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();
}
