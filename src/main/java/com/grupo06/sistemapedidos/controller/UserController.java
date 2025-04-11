package com.grupo06.sistemapedidos.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


// TODO: Crear un global handler para manerjar errore, algunso de los metodos devuelven UserError
@RestController // Indica que esta clase es un controlador REST que manejará solicitudes HTTP
@RequestMapping("/api/user") // Define la ruta base para todos los endpoints de este controlador
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
    @PostMapping("/auth/register")
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

    @GetMapping("/{email}")
    public UsuarioDTO getUser(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
    
    /**
     * Obtener todos los usuarios por su id
     * 
     * @param ids Lista de ids de los usuarios
     * @return List<UsuarioDTO> Lista de DTO para la trasnfarencias de usuarios
     */
    @GetMapping("/all")
    public List<UsuarioDTO> getAllUsers(@RequestParam List<Integer> ids) {
        return userService.getAllUsers(ids);
    }
    
    /**
     * Eliminar un usuario por su id
     * 
     * @param id id del usuario
     * @return void
     */
    @DeleteMapping("/del/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
