package com.grupo06.sistemapedidos.mapper;

import org.springframework.stereotype.Component;
import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.model.Usuario;

/**
 * UserMapper es una clase que se encarga de convertir entre la entidad Usuario y el DTO UsuarioDTO.
 * Está marcada con la anotación @Component para que Spring la detecte como un componente y
 *  pueda inyectarla donde sea necesario.
 */
@Component
public class UserMapper {

    /**
     * Convierte un objeto Usuario a un objeto UsuarioDTO.
     * 
     * @param usuario el objeto Usuario a convertir
     * @return el objeto UsuarioDTO convertido
     */
    public UsuarioDTO toDTO(Usuario usuario){
        return new UsuarioDTO();
    }

    /**
     * Convierte un objeto UsuarioDTO a un objeto Usuario.
     * 
     * @param usuarioDTO el objeto UsuarioDTO a convertir
     * @return el objeto Usuario convertido
     */
    public Usuario toEntity(UsuarioDTO usuarioDTO){
        return new Usuario();
    }
}
