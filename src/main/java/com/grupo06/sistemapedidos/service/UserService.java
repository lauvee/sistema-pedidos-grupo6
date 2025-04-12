package com.grupo06.sistemapedidos.service;

import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.exception.UserError;
import com.grupo06.sistemapedidos.mapper.UserMapper;
import com.grupo06.sistemapedidos.model.Roles;
import com.grupo06.sistemapedidos.model.Usuario;
import com.grupo06.sistemapedidos.repository.RoleRepository;
import com.grupo06.sistemapedidos.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de gestión de usuarios.
 * Esta clase proporciona métodos para registrar, autenticar, y administrar usuarios, así como para recuperar datos relacionados
 * con usuarios desde la base de datos. Además, maneja errores personalizados relacionados con las operaciones de usuarios.
* 
*@Service indica que esta clase es un servicio de Spring y permite la inyección de dependencias.
 */
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final RoleRepository roleRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository,
                       JwtTokenService jwtTokenService, RoleRepository roleRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.roleRepository = roleRepository;
    }

    /**
     * Método para registrar un nuevo usuario.
     *
     * Este método verifica si el usuario ya existe por su correo electrónico. Si no existe, guarda un nuevo usuario en la base de datos.
     * Si el campo `totalSpend` está vacío, se asigna un valor predeterminado de 0.
     *
     * @param userDTO DTO con los datos del usuario a registrar.
     * @return UsuarioDTO DTO con los datos del usuario registrado.
     * @throws UserError Si ocurre un error durante el proceso de registro.
     */
    public UsuarioDTO userRegistry(UsuarioDTO userDTO) throws UserError {
        try {
            Optional<Usuario> optionalUser = userRepository.findByEmail(userDTO.getEmail());
            if (optionalUser.isEmpty()) {
                // Asignar valor por defecto si el totalSpend es nulo
                if (userDTO.getTotalSpend() == null) {
                    userDTO.setTotalSpend(0);
                }
                Usuario newUser = userRepository.save(userMapper.toEntity(userDTO));
                return userMapper.toDTO(newUser);
            } else {
                throw new UserError("El usuario ya existe");
            }
        } catch (Exception ex) {
            throw new UserError("Error al registrar el usuario: " + ex.getMessage());
        }
    }

    /**
     * Método para autenticar un usuario.
     *
     * Este método valida las credenciales del usuario (correo electrónico y contraseña). Si las credenciales son correctas,
     * devuelve los datos del usuario y genera un token JWT.
     *
     * @param userDTO DTO con los datos de inicio de sesión (correo y contraseña).
     * @return UsuarioDTO DTO con los datos del usuario autenticado.
     * @throws UserError Si las credenciales son incorrectas o el usuario no existe.
     */
    public UsuarioDTO userLogin(UsuarioDTO userDTO) throws UserError {
        try {
            Optional<Usuario> optionalUser = userRepository.findByEmail(userDTO.getEmail());
            if (optionalUser.isPresent()) {
                Usuario user = optionalUser.get();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                // Verificar si la contraseña es correcta
                if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                    return new UsuarioDTO(user.getName(), user.getEmail(), user.getRole().toString());
                } else {
                    throw new UserError("Contraseña incorrecta");
                }
            } else {
                throw new UserError("Usuario no encontrado");
            }
        } catch (Exception ex) {
            throw new UserError("Error al iniciar sesión: " + ex.getMessage());
        }
    }

    /**
     * Método para obtener todos los usuarios registrados.
     *
     * Este método devuelve una lista de todos los usuarios registrados en el sistema.
     *
     * @return Lista de DTOs de usuarios registrados.
     * @throws UserError Si ocurre un error al obtener los usuarios.
     */
    public List<UsuarioDTO> getAllRegisteredUsers() throws UserError {
        try {
            List<Usuario> users = userRepository.findAll();
            List<UsuarioDTO> usersDTO = new ArrayList<>();
            users.forEach(user -> usersDTO.add(userMapper.toDTO(user)));
            return usersDTO;
        } catch (Exception ex) {
            throw new UserError("Error al obtener todos los usuarios: " + ex.getMessage());
        }
    }

    /**
     * Método para obtener un usuario por su ID.
     *
     * Este método devuelve un usuario basado en su identificador único.
     *
     * @param id ID del usuario.
     * @return DTO con los datos del usuario.
     * @throws UserError Si no se encuentra el usuario.
     */
    public UsuarioDTO getUserById(Integer id) throws UserError {
        try {
            Optional<Usuario> optionalUser = userRepository.findById(id);
            return optionalUser.map(userMapper::toDTO).orElseThrow(() -> new UserError("Usuario no encontrado"));
        } catch (Exception ex) {
            throw new UserError("Error al obtener el usuario: " + ex.getMessage());
        }
    }

    /**
     * Método para obtener usuarios por una lista de identificadores.
     *
     * Este método permite obtener varios usuarios utilizando una lista de identificadores.
     *
     * @param list Lista de identificadores de usuarios.
     * @return Lista de DTOs de los usuarios.
     * @throws UserError Si ocurre un error al obtener los usuarios.
     */
    public List<UsuarioDTO> getAllUsers(List<Integer> list) throws UserError {
        try {
            Optional<List<Usuario>> optionalUsers = userRepository.findByIdIn(list);
            List<UsuarioDTO> usersDTO = new ArrayList<>();
            optionalUsers.ifPresent(users -> users.forEach(user -> usersDTO.add(userMapper.toDTO(user))));
            return usersDTO;
        } catch (Exception ex) {
            throw new UserError("Error al obtener usuarios: " + ex.getMessage());
        }
    }

    /**
     * Método para eliminar un usuario por su ID.
     *
     * Este método elimina un usuario de la base de datos utilizando su identificador único.
     *
     * @param id ID del usuario a eliminar.
     * @throws UserError Si ocurre un error al eliminar el usuario.
     */
    public void deleteUser(Integer id) throws UserError {
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new UserError("Error al eliminar el usuario: " + ex.getMessage());
        }
    }

    /**
     * Método para obtener un usuario por su correo electrónico.
     *
     * Este método devuelve un usuario utilizando su correo electrónico. También obtiene el rol asociado al usuario.
     *
     * @param email Correo electrónico del usuario.
     * @return DTO con los datos del usuario y su rol.
     * @throws UserError Si ocurre un error al obtener el usuario.
     */
    public UsuarioDTO getUserByEmail(String email) throws UserError {
        try {
            Optional<Usuario> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                Usuario user = optionalUser.get();
                // Obtener el rol del usuario
                Roles role = user.getRole();
                String roleName = role != null ? role.getName().name() : "No Role";  // Nombre del rol (si existe)
                return new UsuarioDTO(user.getName(), user.getEmail(), roleName);
            } else {
                return null; // Usuario no encontrado
            }
        } catch (Exception ex) {
            throw new UserError("Error al obtener el usuario: " + ex.getMessage());
        }
    }
}
