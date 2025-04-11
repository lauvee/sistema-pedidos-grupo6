package com.grupo06.sistemapedidos.mapper;

import com.grupo06.sistemapedidos.repository.RoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.model.Usuario;
import com.grupo06.sistemapedidos.enums.RoleEnum;
import com.grupo06.sistemapedidos.model.Roles;

/**
 * UserMapper es una clase que se encarga de convertir entre la entidad Usuario y el DTO UsuarioDTO.
 * Está marcada con la anotación @Component para que Spring la detecte como un componente y
 *  pueda inyectarla donde sea necesario.
 */
@Component
public class UserMapper {

    private final RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Convierte un objeto Usuario a un objeto UsuarioDTO.
     *
     * @param usuario el objeto Usuario a convertir
     * @return el objeto UsuarioDTO convertido
     */
    public UsuarioDTO toDTO(Usuario usuario) {
        RoleEnum roleEnum = usuario.getRole() != null ? usuario.getRole().getName() : null;

        return new UsuarioDTO(
                usuario.getName(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getSignUpDate(),
                roleEnum
        );
    }

    /**
     * Convierte un objeto UsuarioDTO a un objeto Usuario.
     *
     * @param usuarioDTO el objeto UsuarioDTO a convertir
     * @return el objeto Usuario convertido
     */
    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setName(usuarioDTO.getName());
        usuario.setEmail(usuarioDTO.getEmail());
        // Encriptar la contraseña antes de guardar
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setPassword(encoder.encode(usuarioDTO.getPassword()));
        usuario.setSignUpDate(usuarioDTO.getSignUpDate());
        usuario.setTotalSpend(usuarioDTO.getTotalSpend() != null ? usuarioDTO.getTotalSpend() : 0);

        // Buscar el rol en la base de datos
        RoleEnum roleEnum = RoleEnum.valueOf(usuarioDTO.getRol().toString());
        Roles roleEntity = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleEnum));

        usuario.setRole(roleEntity);

        return usuario;
    }
}
