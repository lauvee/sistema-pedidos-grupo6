package com.grupo06.sistemapedidos.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "PEDIDO", schema = "public")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedido", nullable = false, unique = true)
    private Integer id;

    @NotNull(message = "El usuario no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "usuarioFK", referencedColumnName = "idUser", nullable = false)
    private Usuario usuario;
    
    @NotNull(message = "El producto no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "pedidoFK", referencedColumnName = "idProducto", nullable = false)
    private Producto producto;
}