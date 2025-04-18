package com.grupo06.sistemapedidos.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupo06.sistemapedidos.model.Pedido;
import com.grupo06.sistemapedidos.model.Producto;
import com.grupo06.sistemapedidos.model.Usuario;

/**
 * Interfaz que define los métodos de acceso a datos para la entidad Pedido.
 * Extiende JpaRepository para proporcionar operaciones CRUD y consultas personalizadas.
 *
 * {@link Repository} Anotación de Spring que indica que esta interfaz es un repositorio de acceso a datos.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
    /**
     * Busca un pedido por su identificador.
     *
     * @param id el identificador del pedido
     * @return un {@link Optional} que contiene el pedido si existe, o vacío en caso contrario
     */
    Optional<Pedido> findById(int id);
    
    /**
     * Recupera la lista de todos los pedidos.
     *
     * @return una lista con todos los pedidos almacenados
     */
    List<Pedido> findAll();
    
    /**
     * Recupera la lista de pedidos asociados a un usuario dado.
     *
     * @param usuario la entidad {@link Usuario} a la que está asociado el pedido
     * @return una lista de pedidos pertenecientes al usuario especificado
     */
    List<Pedido> findByUsuario(Usuario usuario);
    
    /**
     * Recupera la lista de pedidos basándose en el identificador del usuario.
     *
     * @param usuarioId el identificador del usuario
     * @return una lista de pedidos asociados al usuario con el identificador proporcionado
     */
    List<Pedido> findByUsuarioId(Integer usuarioId);
    
    /**
     * Recupera la lista de pedidos que contienen un producto específico.
     *
     * @param producto la entidad {@link Producto} que se busca dentro de los pedidos
     * @return una lista de pedidos que incluyen el producto especificado
     */
    List<Pedido> findByProductos(Producto producto);
    
    /**
     * Recupera la lista de pedidos asociados a un usuario y que contengan un producto específico.
     *
     * @param usuario la entidad {@link Usuario} a la que está asociado el pedido
     * @param producto la entidad {@link Producto} que debe estar incluida en el pedido
     * @return una lista de pedidos que cumplen ambos criterios
     */
    List<Pedido> findByUsuarioAndProductos(Usuario usuario, Producto producto);
}
