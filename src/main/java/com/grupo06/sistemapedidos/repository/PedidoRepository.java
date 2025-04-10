package com.grupo06.sistemapedidos.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupo06.sistemapedidos.model.Pedido;
import com.grupo06.sistemapedidos.model.Producto;
import com.grupo06.sistemapedidos.model.Usuario;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    Optional<Pedido> findById(int id);
    List<Pedido> findAll();
    List<Pedido> findByUsuario(Usuario usuario);
    List<Pedido> findByUsuarioId(Integer usuarioId);
    List<Pedido> findByProducto(Producto producto);
    List<Pedido> findByProductoId(Integer productoId);
    List<Pedido> findByUsuarioAndProducto(Usuario usuario, Producto producto);
}
