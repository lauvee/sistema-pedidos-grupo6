package com.grupo06.sistemapedidos.controller;

import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.exception.UserError;
import com.grupo06.sistemapedidos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registrar un usuario
     *
     * @param userDTO Datos del usuario
     * @return Respuesta con el usuario registrado
     * @throws UserError Si ocurre un error durante el registro
     */
    @PostMapping("auth/register")
    @Operation(summary = "Registrar un nuevo usuario", description = "Permite registrar un usuario sin necesidad de autenticación.")
    public ResponseEntity<UsuarioDTO> registerUser(@RequestBody UsuarioDTO userDTO) throws UserError {
        return ResponseEntity.ok(userService.userRegistry(userDTO));
    }

    /**
     * Login de usuario
     */
    @PostMapping("auth/login")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Login de usuario", description = "Permite el inicio de sesión de un usuario existente.")
    public ResponseEntity<UsuarioDTO> userLogin(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(userService.userLogin(usuarioDTO));
    }

    /**
     * Obtener todos los usuarios registrados
     *
     * @return Lista de usuarios registrados
     * @throws UserError Si ocurre un error al obtener los usuarios
     */
    @GetMapping("/usersAll")
    @PreAuthorize("hasRole('ADMIN')")  // Solo accesible por usuarios con el rol ADMIN
    @Operation(summary = "Obtener todos los usuarios", description = "Este endpoint requiere autenticación JWT.")
    public ResponseEntity<List<UsuarioDTO>> getAllUsers() throws UserError {
        List<UsuarioDTO> usersDTO = userService.getAllRegisteredUsers();
        return ResponseEntity.ok(usersDTO);
    }

    /**
     * Obtener todos los usuarios registrados con la id especificada
     *
     * @return Lista de usuarios
     * @throws UserError Si ocurre un error al obtener los usuarios
     */
    @GetMapping("/usersAll/{ids}")
    @PreAuthorize("hasRole('ADMIN')")  // Solo accesible por usuarios con el rol ADMIN
    @Operation(summary = "Obtener todos los usuarios", description = "Este endpoint requiere autenticación JWT.")
    public ResponseEntity<List<UsuarioDTO>> getAllUsers(@RequestParam List<Integer> ids) throws UserError {
        return ResponseEntity.ok(userService.getAllUsers(ids));
    }

    /**
     * Obtener un usuario por su ID
     *
     * @param id ID del usuario
     * @return UsuarioDTO con los datos del usuario
     * @throws UserError Si ocurre un error al obtener el usuario
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")  // Accesible por usuarios con los roles USER o ADMIN
    @Operation(summary = "Obtener un usuario por ID", description = "Este endpoint requiere autenticación JWT.")
    public ResponseEntity<UsuarioDTO> getUserById(@PathVariable Integer id) throws UserError {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Eliminar un usuario por su ID
     *
     * @param id ID del usuario
     * @throws UserError Si ocurre un error al eliminar el usuario
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // Solo accesible por usuarios con el rol ADMIN
    @Operation(summary = "Eliminar un usuario por ID", description = "Este endpoint requiere autenticación JWT.")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) throws UserError {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}