package com.grupo06.sistemapedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grupo06.sistemapedidos.model.Producto;

import jakarta.transaction.Transactional;

/**
 * Interfaz que define los métodos de acceso a datos para la entidad Producto.
 * Extiende JpaRepository para proporcionar operaciones CRUD y consultas personalizadas.
 *
 * {@link Repository} Anotación de Spring que indica que esta interfaz es un repositorio de acceso a datos.
 */
@Repository
public interface ProductRepository extends JpaRepository<Producto, Integer> {
    /**
     * Busca un producto por su nombre.
     *
     * @param name Nombre del producto a buscar.
     * @return Un Optional que contiene el producto encontrado, o vacío si no se encuentra.
     */
    Optional<Producto> findByName(String name);

    /**
     * Elimina un producto por su nombre.
     *
     * @param name Nombre del producto a eliminar.
     */
    void deleteByName(String name);

    /**
     * Busca todos los productos que coinciden con el nombre dado.
     * 
     * @param name Nombre del producto a buscar.
     * @return Lista de productos que coinciden con el nombre dado.
     */
    List<Producto> findAllByName(String name);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM pedido_producto WHERE producto_id = :productoId", nativeQuery = true)
    void deletePedidoProductoByProductoId(Integer productoId);
}