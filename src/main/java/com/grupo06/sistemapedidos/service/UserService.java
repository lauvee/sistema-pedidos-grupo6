package com.grupo06.sistemapedidos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.exception.UserError;
import com.grupo06.sistemapedidos.mapper.UserMapper;
import com.grupo06.sistemapedidos.model.Usuario;
import com.grupo06.sistemapedidos.repository.UserRepository;

@Service
public class UserService {
    private UserMapper userMapper;
    private UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository)  {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    /**
     * Registrar un usuario
     * 
     * @param email email del suaurio
     * @return UsuarioDTO DTO para la trasnfarencias de usuarios
     * @throws UserError Error personalziado de usuario
     */
    public UsuarioDTO userRegistry(UsuarioDTO userDTO) throws UserError {
        try {
            Optional<Usuario> optionalUser = userRepository.findByEmail(userDTO.getEmail());
            if(!optionalUser.isPresent()){
                // El usuario no existe poodemos registrarnos en la base de datos
                Usuario newUser = userRepository.save(userMapper.toEntity(userDTO));
                // Lo convertimos a DTO y lo devolvemos
                return userMapper.toDTO(newUser);
                // TODO: Creamos unn token de autentificacion para el usuario con jwt
            } else {
                // El usuario ya existe
                throw new UserError("El usuario ya existe");
            }
        } catch (Exception ex) {
            throw new UserError("Error al registrar el usuario: " + ex.getMessage());
        }
    }

     /**
     * Logear un usuario
     * 
     * @param email email del suaurio
     * @return UsuarioDTO DTO para la trasnfarencias de usuarios
     * @throws UserError Error personalziado de usuario
     */
    public UsuarioDTO userLogin(UsuarioDTO userDTO) throws UserError {
        try {
            Optional<Usuario> newUser = userRepository.findByEmail(userDTO.getEmail());
            if(newUser.isPresent()){
                // El usuario existe
               // TODO: Comprobar la contraseña y crear un token de autentificacion para el usuario con jwt
            }
            return null;
        } catch (Exception ex) {
            throw new UserError("Error al registrar el usuario: " + ex.getMessage());
        }
    }


    /**
     * Devuelbe el usuairo pasandole por parametro la id de este
     * 
     * @param UsuarioDTO DTO para la trasnfarencias de usuarios
     * @return UsuarioDTO DTO para la trasnfarencias de usuarios
     * @throws UserError Error personalziado de usuario
     */
    public UsuarioDTO getUser(UsuarioDTO userDTO) throws UserError {
        try {
            Optional<Usuario> newUser = userRepository.findByEmail(userDTO.getEmail());
            if(newUser.isPresent()){
                return userMapper.toDTO(newUser.get());
            }
            return null;
        } catch (Exception ex) {
            throw new UserError("Error al registrar el usuario: " + ex.getMessage());
        }
    }

    /**
     * Obtiene todos los usuarios pasandole una lista con sus id
     * 
     * @param list lista de identificadores de usuarios
     * @return List<UsuarioDTO> lista de usuarios
     * @throws UserError Error personalziado de usuario
     */
    public List<UsuarioDTO> getAllUsers(List<Integer> list) throws UserError {
        Optional<List<Usuario>> userOoptionalList = userRepository.findByIdIn(list);
        List<UsuarioDTO> usersDTO = new ArrayList<>();

        if(userOoptionalList.isPresent()){
            List<Usuario> userList = userOoptionalList.get();
            usersDTO = userList.stream()
            .map(userMapper::toDTO)
            .toList();
        }
                                                
        return usersDTO;
    }

    /**
     * Elimina un usuario pasandole por parametro la id de este
     * 
     * @param id id del usuario a eliminar
     * @throws UserError Error personalziado de usuario
     */
    public void deleteUser(Integer id) throws UserError {
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new UserError("Error al eliminar el usuario: " + ex.getMessage());
        }
    }
}
