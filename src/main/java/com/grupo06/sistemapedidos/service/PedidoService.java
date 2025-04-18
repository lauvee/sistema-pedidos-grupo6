package com.grupo06.sistemapedidos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.dto.PedidoDTO;
import com.grupo06.sistemapedidos.enums.ApiError;
import com.grupo06.sistemapedidos.exception.RequestException;
import com.grupo06.sistemapedidos.mapper.PedidoMapper;
import com.grupo06.sistemapedidos.model.Pedido;
import com.grupo06.sistemapedidos.model.Producto;
import com.grupo06.sistemapedidos.model.Usuario;
import com.grupo06.sistemapedidos.repository.PedidoRepository;
import com.grupo06.sistemapedidos.repository.ProductRepository;
import com.grupo06.sistemapedidos.repository.UserRepository;

/**
 * Clase de servicio para manejar la lógica de negocio relacionada con los pedidos.
 * Proporciona métodos para obtener, crear y eliminar pedidos.
 * 
 * @Service indica que esta clase es un servicio de Spring y permite la inyección de dependencias.
 */
@Service
public class PedidoService {
    /**
     * PedidoMapper es un objeto que se encarga de convertir entre entidades y DTOs.
     * PedidoRepository es un objeto que se encarga de interactuar con la base de datos.
     * ProductRepository es un objeto que se encarga de interactuar con la base de datos de productos.
     * UserService es un objeto que se encarga de manejar la lógica de negocio relacionada con los usuarios.
     * UserMapper es un objeto que se encarga de convertir entre entidades y DTOs de usuarios.
     */
    private final PedidoMapper pedidoMapper; 
    private final PedidoRepository pedidoRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final KafkaProducerService kafkaProducerService;

    public PedidoService (PedidoMapper pedidoMapper, PedidoRepository pedidoRepository, UserRepository userRepository, ProductRepository productRepository, KafkaProducerService kafkaProducerService) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    /**
     * Obtiene un pedido por su id.
     * 
     * @param id ID del pedido a obtener
     * @return PedidoDTO DTO para la transferencia de pedidos, pedido encontrado
     */
    public PedidoDTO getPedidoById(int id)  {
        try {
            Optional<Pedido> newPedidoOptional = pedidoRepository.findById(id);
            if(!newPedidoOptional.isPresent())
                throw new RequestException(ApiError.PEDIDO_NOT_FOUND);
            
            Pedido newPedido = newPedidoOptional.get();
            PedidoDTO newPedidoDTO = pedidoMapper.toDTO(newPedido);
            return newPedidoDTO;
        } catch (RequestException e) {
            throw e;
        } catch (Exception e) {
           throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    public PedidoDTO getPedidoByNombre(String nombre)  {
        try {
            Optional<Pedido> newPedidoOptional = pedidoRepository.findById(1);
            PedidoDTO newPedidoDTO = null;
            if(newPedidoOptional.isPresent()){
                Pedido newPedido = newPedidoOptional.get();
                newPedidoDTO = pedidoMapper.toDTO(newPedido);
            }
            return newPedidoDTO;
        } catch (Exception e) {
           throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }
    
     /**
     * Obtiene todos los pedidos.
     * 
     * @return List<PedidoDTO> DTO para la transferencia de pedidos
     */
    public List<PedidoDTO> getAllPedidos()  {
        try {
        List<Pedido> listaPedidos = pedidoRepository.findAll();
        List<PedidoDTO> listaPedidosDTO = listaPedidos.stream()
                .map(pedidoMapper::toDTO)
                .toList();

        return listaPedidosDTO;
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
        
    }

      /**
     * Crea un nuevo pedido.
     * 
     * @param pedidoDTO DTO para la transferencia de pedidos
     * @return PedidoDTO DTO para la transferencia de pedidos
     */
    public PedidoDTO postPedido(PedidoDTO pedidoDTO) {
        try {
            // Verificamos si el usuario que hace la peticion ya hizo un pedido
            if(pedidoRepository.existsByUsuarioId(pedidoDTO.getUsuario()))
                throw new RequestException(ApiError.USER_ALREADY_HAS_ORDER);

            // Añadmir un evento al topic de Kafka
          //2 kafkaProducerService.sendOrderCreated("Nuevo pedido de usuario " + pedidoDTO.getUsuario() + " con productos " + pedidoDTO.getProductos());

            // Obtenemos el usuario de la fk
            Usuario usuarioEntity = getUsuarioEntityByFK(pedidoDTO);
            List<Producto> listaProductos = getListProductosFK(pedidoDTO);

            // Devemos pasarle el usuario asoicado al pedido y la lista de productos
            Pedido newPedido = pedidoMapper.toEntity(usuarioEntity, listaProductos);
            Pedido pedidoSave = pedidoRepository.save(newPedido);
            return pedidoMapper.toDTO(pedidoSave);
        } catch (RequestException e) {
            // Tanto getUsuarioEntityByFK como getListProductosFK pueden lanzar una RequestException
            // si no se encuentra el usuario o el producto, por lo que la excepción se lanza y se maneja aquí
            throw e;
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un pedido existente.
     * 
     * @param pedidoDTO DTO para la transferencia de pedidos
     * @return PedidoDTO DTO para la transferencia de pedidos
     */
    public PedidoDTO putPedidoById(PedidoDTO pedidoDTO) {
        try {
            String eventMessage = "Pedido modificado de usuario " + pedidoDTO.getUsuario() + " con productos " + pedidoDTO.getProductos();
          //2323  kafkaProducerService.sendModificationNotification(eventMessage);

            Usuario usuarioEntity = getUsuarioEntityByFK(pedidoDTO);
            List<Producto> listaProductos = getListProductosFK(pedidoDTO);

            Pedido newPedido = pedidoMapper.toEntity(usuarioEntity, listaProductos);
            Pedido pedidoSave = pedidoRepository.save(newPedido);
            return pedidoMapper.toDTO(pedidoSave);
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Elimina un pedido por su id.
     * 
     * @param id ID del pedido a eliminar
     */
    public void deletePedidoById(Integer id){
        try {
            String eventMessage = "Pedido eliminado con id " + id;
           //123 kafkaProducerService.sendCancellationNotification(eventMessage);
            // Eliminar el pedido de la base de datos
            pedidoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Método para obtener todos los productos asicaidos a traves de una lista de IDs
     * 
     * @param pedidoDTO DTO para la transferencia de pedidos, se compone de el id del usuario y una lista de ids de productos
     * @return List<Producto> lista de productos asociados al pedido
     */
    public List<Producto> getListProductosFK(PedidoDTO pedidoDTO) {
        try {
            List<Producto> listaProductos = new ArrayList<>();
            for(Integer idPedido : pedidoDTO.getProductos()){
                Optional<Producto> newProducto = productRepository.findById(idPedido);
                // Verificamos que el producto existe, si no existe lanzamos una excepción
                if(!newProducto.isPresent())
                    throw new RequestException(ApiError.PRODUCT_NOT_FOUND);
                listaProductos.add(newProducto.get());
            }
            return listaProductos;
        } catch (RequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método para obtener el usuario asociado al pedido a traves de su ID
     * 
     * @param pedidoDTO DTO para la transferencia de pedidos, se compone de el id del usuario y una lista de ids de productos
     * @return Usuario usuario asociado al pedido
     */
    public Usuario getUsuarioEntityByFK(PedidoDTO pedidoDTO) throws Exception {
        try {
            // Obtenemos el usuario si no existe lanzamos una excepción
            Optional<Usuario> newUsuario = userRepository.findById(pedidoDTO.getUsuario());
            if(!newUsuario.isPresent())
                throw new RequestException(ApiError.USER_NOT_FOUND);
            
            return newUsuario.get();
        } catch (RequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
        
    }
}
