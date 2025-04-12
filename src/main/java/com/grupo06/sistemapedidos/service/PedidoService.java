package com.grupo06.sistemapedidos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.dto.PedidoDTO;
import com.grupo06.sistemapedidos.dto.ProductDTO;
import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.enums.ApiError;
import com.grupo06.sistemapedidos.exception.RequestException;
import com.grupo06.sistemapedidos.mapper.PedidoMapper;
import com.grupo06.sistemapedidos.mapper.ProductMapper;
import com.grupo06.sistemapedidos.mapper.UserMapper;
import com.grupo06.sistemapedidos.model.Pedido;
import com.grupo06.sistemapedidos.model.Producto;
import com.grupo06.sistemapedidos.model.Usuario;
import com.grupo06.sistemapedidos.repository.PedidoRepository;

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
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper; 
    private final UserService userService;
    private final UserMapper userMapper;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final KafkaProducerService kafkaProducerService;

    public PedidoService (PedidoMapper pedidoMapper, PedidoRepository pedidoRepository, UserService userService, UserMapper userMapper, ProductService productService, ProductMapper productMapper, KafkaProducerService kafkaProducerService) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.productService = productService;
        this.productMapper = productMapper;
        this.kafkaProducerService = kafkaProducerService;
    }

    /**
     * Obtiene un pedido por su id.
     * 
     * @param id ID del pedido a obtener
     * @return PedidoDTO DTO para la transferencia de pedidos, pedido encontrado
     * @throws Exception
     */
    public PedidoDTO getPedido(int id)  {
        try {
            Optional<Pedido> newPedidoOptional = pedidoRepository.findById(id);
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
     * @throws Exception
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
     * @throws Exception
     */
    public PedidoDTO postPedido(PedidoDTO pedidoDTO) {
        try {
            // Añadmir un evento al topic de Kafka
            String eventMessage = "Nuevo pedido de usuario " + pedidoDTO.getUsuario() + " con productos " + pedidoDTO.getProductos();
            kafkaProducerService.sendCreationNotification(eventMessage);

            Usuario usuarioEntity = getUsuarioEntityByFK(pedidoDTO);
            List<Producto> listaProductos = getListProductosFK(pedidoDTO);

            // Devemos pasarle el usuario asoicado al pedido y la lista de productos
            Pedido newPedido = pedidoMapper.toEntity(usuarioEntity, listaProductos);
            Pedido pedidoSave = pedidoRepository.save(newPedido);
            return pedidoMapper.toDTO(pedidoSave);
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un pedido existente.
     * 
     * @param pedidoDTO DTO para la transferencia de pedidos
     * @return PedidoDTO DTO para la transferencia de pedidos
     * @throws Exception
     */
    public PedidoDTO putPedido(PedidoDTO pedidoDTO) {
        try {
            String eventMessage = "Pedido modificado de usuario " + pedidoDTO.getUsuario() + " con productos " + pedidoDTO.getProductos();
            kafkaProducerService.sendModificationNotification(eventMessage);

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
    public void deletePedido(Integer id){
        try {
            String eventMessage = "Pedido eliminado con id " + id;
            kafkaProducerService.sendCancellationNotification(eventMessage);
            // Eliminar el pedido de la base de datos
            pedidoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
        
    }

    /// Utilidades
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
                ProductDTO childPedido = productService.findById(idPedido);
                listaProductos.add(productMapper.toEntity(childPedido));
            }
            return listaProductos;
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
            UsuarioDTO usuario = userService.getUserById(pedidoDTO.getUsuario());
            Usuario usuarioEntity = userMapper.toEntity(usuario);
            return usuarioEntity;
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
        
    }
}
