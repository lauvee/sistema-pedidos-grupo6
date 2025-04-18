package com.grupo06.sistemapedidos.service;

import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.enums.ApiError;
import com.grupo06.sistemapedidos.enums.RoleEnum;
import com.grupo06.sistemapedidos.exception.RequestException;
import com.grupo06.sistemapedidos.mapper.UserMapper;
import com.grupo06.sistemapedidos.model.Usuario;
import com.grupo06.sistemapedidos.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserService(UserMapper userMapper, UserRepository userRepository,
                       JwtTokenService jwtTokenService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
    }

    /**
     * Método para registrar un nuevo usuario.
     *
     * Este método verifica si el usuario ya existe por su correo electrónico. Si no existe, guarda un nuevo usuario en la base de datos.
     * Si el campo `totalSpend` está vacío, se asigna un valor predeterminado de 0.
     * {@link Authentication} Se utiliza para verificar si el usuario que está creando un nuevo usuario es un administrador.
     * {@link JwtTokenService} Se utiliza para encriptar la contraseña del usuario antes de guardarla.
     * 
     * @param userDTO DTO con los datos del usuario a registrar.
     * @return UsuarioDTO DTO con los datos del usuario registrado.
     */
    public UsuarioDTO userRegistry(UsuarioDTO userDTO) {
        try {
            // Obtenemos el contexto de Spring Security para verificar si el usuario que está creando un admin es también admin
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ADMIN"));

            // Si estamos creando un usuario admin y el usuario que lo crea no es admin, lanzamos una excepción
            if(userDTO.getRol() == RoleEnum.ADMIN && isAdmin)
                throw new RequestException(ApiError.FORBIDDEN_CREATE_ADMIN);

            Optional<Usuario> optionalUser = userRepository.findByEmailAndName(userDTO.getEmail(), userDTO.getName());
            if (!optionalUser.isPresent()) {
                // Si el usuario no existe, se procede a crear uno nuevo, generamos el token y lo encriptamos
                Usuario usuario = userMapper.toEntity(userDTO);
                
                // Utilizamos BCryptPasswordEncoder para encriptar la contraseña
                usuario.setPassword(jwtTokenService.encodePassword(userDTO.getPassword()));
                // Lo almacenamos en la base de datos
                Usuario newUser = userRepository.save(usuario);
                return userMapper.toDTO(newUser);
            } else {
                throw new RequestException(ApiError.DUPLICATE_RESOURCE);
            }
        } catch(RequestException e) {
            throw e;
        } catch (Exception ex) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
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
     */
    public UsuarioDTO userLogin(UsuarioDTO userDTO) {
        try {
            Optional<Usuario> optionalUser = userRepository.findByEmailAndName(userDTO.getEmail(), userDTO.getName());
            if (optionalUser.isPresent()) {
                Usuario user = optionalUser.get();
                // Verificar si la contraseña es correcta, le pasamos la contraseña en texto plano junto con la contraseña encriptada correspondiente a ese usuario
                if (jwtTokenService.matchesPassword(userDTO.getPassword(), user.getPassword())) {
                    System.out.println("Contraseña correcta para el usuario: " + user.getEmail());
                    // Obtenemos el topken de sesion del usuario
                    String token = jwtTokenService.generateTokenWithRole(user.getEmail(), user.getRole().getName());
                    UsuarioDTO newUsuarioDTO = 
                    new UsuarioDTO(
                        user.getName(),
                        user.getEmail(),
                        user.getSignUpDate(),
                        user.getTotalSpend(),
                        user.getRole().getName()
                    );
                    newUsuarioDTO.setToken(token); 
                    return newUsuarioDTO;
                } else {
                    throw new RequestException(ApiError.AUTHENTICATION_FAILED);
                }
            } else {
                throw new RequestException(ApiError.USER_NOT_FOUND);
            }
        } catch(RequestException e) {
            throw e;
        } catch (Exception ex) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método para obtener todos los usuarios registrados.
     *
     * Este método devuelve una lista de todos los usuarios registrados en el sistema.
     *
     * @return Lista de DTOs de usuarios registrados.
     */
    public List<UsuarioDTO> getAllRegisteredUsers() {
        try {
            List<Usuario> users = userRepository.findAll();
            List<UsuarioDTO> usersDTO = new ArrayList<>();
            users.forEach(user -> usersDTO.add(userMapper.toDTO(user)));
            return usersDTO;
        } catch (Exception ex) {
            throw new RequestException(ApiError.FORBIDDEN);
        }
    }

    /**
     * Método para obtener un usuario por su ID.
     *
     * Este método devuelve un usuario basado en su identificador único.
     *
     * @param id ID del usuario.
     * @return DTO con los datos del usuario.
     */
    public UsuarioDTO getUserById(Integer id) {
        try {
            Optional<Usuario> optionalUser = userRepository.findById(id);
            if (!optionalUser.isPresent()) {
                throw new RequestException(ApiError.USER_NOT_FOUND);
            }
            return userMapper.toDTO(optionalUser.get());
        } catch (Exception ex) {
            throw new RequestException(ApiError.USER_NOT_FOUND);
        }
    }

    /**
     * Método para obtener usuarios por una lista de identificadores.
     *
     * Este método permite obtener varios usuarios utilizando una lista de identificadores.
     *
     * @param list Lista de identificadores de usuarios.
     * @return Lista de DTOs de los usuarios.
     */
    public List<UsuarioDTO> getAllUsers(List<Integer> list) {
        try {
            Optional<List<Usuario>> optionalUsers = userRepository.findByIdIn(list);
            List<UsuarioDTO> usersDTO = new ArrayList<>();
            optionalUsers.ifPresent(users -> users.forEach(user -> usersDTO.add(userMapper.toDTO(user))));
            return usersDTO;
        } catch (Exception ex) {
            throw new RequestException(ApiError.USER_NOT_FOUND);
        }
    }

    /**
     * Método para obtener un usuario por su correo electrónico.
     *
     * Este método devuelve un usuario utilizando su correo electrónico. También obtiene el rol asociado al usuario.
     *
     * @param email Correo electrónico del usuario.
     * @return DTO con los datos del usuario y su rol.
     */
    public UsuarioDTO getUserByEmail(String email) {
        try {
            Optional<Usuario> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                Usuario user = optionalUser.get();

                // Obtenemos su token correspondiente se lo asignamos y lo devolvemos
                String token = jwtTokenService.generateTokenWithRole(user.getEmail(), user.getRole().getName());
                UsuarioDTO usuarioDTO = new UsuarioDTO(
                    user.getName(),
                    user.getEmail(),
                    user.getSignUpDate(),
                    user.getTotalSpend(),
                    user.getRole().getName()
                );
                usuarioDTO.setToken(token);
                return usuarioDTO;
            } else {
                return null; // Usuario no encontrado
            }
        } catch (Exception ex) {
            throw new RequestException(ApiError.USER_NOT_FOUND);
        }
    }

    /**
     * Método para actualizar un usuario por su ID.
     * 
     * @param id ID del usuario a actualizar
     * @param entity DTO con los nuevos datos del usuario
     * @return UsuarioDTO DTO con los datos actualizados del usuario
     */
    public UsuarioDTO putUserById(Integer id, UsuarioDTO entity) {
        try {
            Optional<Usuario> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()  && optionalUser.get().getId().equals(id)) {
                Usuario newUser = userMapper.toEntity(entity);
                newUser.setId(id); 

                return userMapper.toDTO(newUser);
            } else {
                throw new RequestException(ApiError.USER_NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método para actualizar un usuario por su correo electrónico.
     * 
     * @param email Correo electrónico del usuario a actualizar
     * @param entity DTO con los nuevos datos del usuario
     * @return UsuarioDTO DTO con los datos actualizados del usuario
     */
    public UsuarioDTO putUserByEmail(String email, UsuarioDTO entity) {
        try {
            Optional<Usuario> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()  && optionalUser.get().getEmail().equals(email)) {
                Usuario user = userMapper.toEntity(entity);
                user.setId(optionalUser.get().getId()); 
                return userMapper.toDTO(user);

            } else {
                throw new RequestException(ApiError.USER_NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método para eliminar un usuario por su ID.
     * Este método elimina un usuario de la base de datos utilizando su identificador único.
     *
     * @param id ID del usuario a eliminar.
     */
    public void deleteUser(Integer id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new RequestException(ApiError.USER_DELETE_FAILED);
        }
    }

    /**
     * Método para eliminar un usuario por su correo electrónico.
     * 
     * @param email Correo electrónico del usuario a eliminar
     */
    public void deleteUserByEmail(String email) {
        try {
            Optional<Usuario> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                userRepository.delete(optionalUser.get());
            } else {
                throw new RequestException(ApiError.USER_NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new RequestException(ApiError.USER_DELETE_FAILED);
        }
    }
}
