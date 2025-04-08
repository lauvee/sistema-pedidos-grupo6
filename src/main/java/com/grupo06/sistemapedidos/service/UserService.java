package com.grupo06.sistemapedidos.service;

import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.exception.UserError;
import com.grupo06.sistemapedidos.mapper.UserMapper;
import com.grupo06.sistemapedidos.model.Usuario;
import com.grupo06.sistemapedidos.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;  // Token JWT

    public UserService(UserMapper userMapper, UserRepository userRepository, JwtTokenService jwtTokenService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
    }

    /**
     * Registrar un usuario
     *
     * @param userDTO DTO con los datos del usuario
     * @return UsuarioDTO DTO con los datos del usuario registrado
     * @throws UserError Error personalizado de usuario
     */
    public UsuarioDTO userRegistry(UsuarioDTO userDTO) throws UserError {
        try {
            Optional<Usuario> optionalUser = userRepository.findByEmail(userDTO.getEmail());
            if (optionalUser.isEmpty()) {
                // El usuario no existe, se puede registrar
                Usuario newUser = userRepository.save(userMapper.toEntity(userDTO));
                // Generar un token JWT
                String token = jwtTokenService.generateToken(userDTO.getEmail());
                userDTO.setToken(token);  // Asignar el token generado
                return userMapper.toDTO(newUser);
            } else {
                throw new UserError("El usuario ya existe");
            }
        } catch (Exception ex) {
            throw new UserError("Error al registrar el usuario: " + ex.getMessage());
        }
    }

    /**
     * Logear un usuario
     *
     * @param userDTO DTO con los datos del usuario (email y contraseña)
     * @return UsuarioDTO DTO con los datos del usuario y token
     * @throws UserError Error personalizado de usuario
     */
    public UsuarioDTO userLogin(UsuarioDTO userDTO) throws UserError {
        try {
            Optional<Usuario> optionalUser = userRepository.findByEmail(userDTO.getEmail());
            if (optionalUser.isPresent()) {
                Usuario user = optionalUser.get();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                // Comprobar la contraseña
                if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                    // Generar un token JWT
                    String token = jwtTokenService.generateToken(user.getEmail());
                    return new UsuarioDTO(user.getName(), user.getEmail(), token);
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
     * Obtener un usuario por email
     *
     * @param userDTO DTO con los datos del usuario (email)
     * @return UsuarioDTO DTO con los datos del usuario
     * @throws UserError Error personalizado de usuario
     */
    public UsuarioDTO getUser(UsuarioDTO userDTO) throws UserError {
        try {
            Optional<Usuario> optionalUser = userRepository.findByEmail(userDTO.getEmail());
            return optionalUser.map(userMapper::toDTO).orElse(null);
        } catch (Exception ex) {
            throw new UserError("Error al obtener el usuario: " + ex.getMessage());
        }
    }

    /**
     * Obtener todos los usuarios pasando una lista de identificadores
     *
     * @param list Lista de identificadores de usuarios
     * @return List<UsuarioDTO> Lista de usuarios
     * @throws UserError Error personalizado de usuario
     */
    public List<UsuarioDTO> getAllUsers(List<Integer> list) throws UserError {
        try {
            Optional<List<Usuario>> optionalUsers = userRepository.findByIdIn(list);
            List<UsuarioDTO> usersDTO = new ArrayList<>();
            optionalUsers.ifPresent(users -> usersDTO.add(userMapper.toDTO((Usuario) users)));
            return usersDTO;
        } catch (Exception ex) {
            throw new UserError("Error al obtener usuarios: " + ex.getMessage());
        }
    }

    /**
     * Eliminar un usuario por ID
     *
     * @param id ID del usuario a eliminar
     * @throws UserError Error personalizado de usuario
     */
    public void deleteUser(Integer id) throws UserError {
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new UserError("Error al eliminar el usuario: " + ex.getMessage());
        }
    }

    /**
     * Actualizar un usuario existente
     *
     * @param id      El ID del usuario a actualizar
     * @param userDTO DTO con los nuevos datos del usuario
     * @return UsuarioDTO DTO con los datos actualizados del usuario
     * @throws UserError Error personalizado de usuario
     */
    public UsuarioDTO updateUser(Integer id, UsuarioDTO userDTO) throws UserError {
        try {
            Optional<Usuario> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                Usuario user = optionalUser.get();
                // Actualizamos los campos que puedan ser modificados
                user.setName(userDTO.getName());
                user.setEmail(userDTO.getEmail());
                user.setTotalSpent(userDTO.getTotalSpend());

                // Guardamos el usuario actualizado
                userRepository.save(user);
                return userMapper.toDTO(user);
            } else {
                throw new UserError("Usuario no encontrado");
            }
        } catch (Exception ex) {
            throw new UserError("Error al actualizar el usuario: " + ex.getMessage());
        }
    }

    /**
     * Cambiar la contraseña de un usuario
     *
     * @param id          ID del usuario cuyo password se actualizará
     * @param newPassword Nueva contraseña
     * @throws UserError Error personalizado de usuario
     */
    public void changePassword(Integer id, String newPassword) throws UserError {
        try {
            Optional<Usuario> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                Usuario user = optionalUser.get();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            } else {
                throw new UserError("Usuario no encontrado");
            }
        } catch (Exception ex) {
            throw new UserError("Error al cambiar la contraseña: " + ex.getMessage());
        }
    }
}
