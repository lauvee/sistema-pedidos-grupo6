package com.grupo06.sistemapedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo06.sistemapedidos.model.Producto;

/**
 * Interfaz que define los métodos de acceso a datos para la entidad Producto.
 * Extiende JpaRepository para proporcionar operaciones CRUD y consultas personalizadas.
 *
 * {@link Repository} Anotación de Spring que indica que esta interfaz es un repositorio de acceso a datos.
 */
@Repository
public interface ProductRepository extends JpaRepository<Producto, Integer> {
}