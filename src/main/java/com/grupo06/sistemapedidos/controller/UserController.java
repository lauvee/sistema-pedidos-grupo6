package com.grupo06.sistemapedidos.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


// TODO: Crear un global handler para manerjar errore, algunso de los metodos devuelben UserError
@Controller
public class UserController {
    private final UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    /**
     * Registrar un usuario
     * 
     * @param usuarioDTO DTO para la transferencia de usuarios
     * @return UsuarioDTO DTO para la trasnfarencias de usuarios
     */
    @PostMapping("/auth/user")
    public UsuarioDTO userRegistry(@RequestParam UsuarioDTO usuarioDTO) {
        return userService.userRegistry(usuarioDTO);
    }

    /**
     * Logear un usuario
     * 
     * @param usuarioDTO DTO para la transferencia de usuarios
     * @return UsuarioDTO DTO para la trasnfarencias de usuarios
     */
    @PostMapping("/auth/login")
    public UsuarioDTO userLoggin(@RequestParam UsuarioDTO usuarioDTO) {
        return userService.userLogin(usuarioDTO);
    } 

    /***
     * Obtener un usuario por su id
     * 
     * @param usuarioDTO DTO para la transferencia de usuarios
     * @return UsuarioDTO DTO para la trasnfarencias de usuarios
     */

    @GetMapping("/user")
    public UsuarioDTO getUser(@RequestParam UsuarioDTO usuarioDTO) {
        return userService.getUser(usuarioDTO);
    }
    
    /**
     * Obtener todos los usuarios por su id
     * 
     * @param ids Lista de ids de los usuarios
     * @return List<UsuarioDTO> Lista de DTO para la trasnfarencias de usuarios
     */
    @GetMapping("/users")
    public List<UsuarioDTO> getAllUsers(@RequestParam List<Integer> ids) {
        return userService.getAllUsers(ids);
    }
    
    /**
     * Eliminar un usuario por su id
     * 
     * @param id id del usuario
     * @return void
     */
    @DeleteMapping("/user")
    public void deleteUser(@RequestParam Integer id) {
        userService.deleteUser(id);
    }
}
