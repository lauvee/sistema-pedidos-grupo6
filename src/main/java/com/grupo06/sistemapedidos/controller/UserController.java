package com.grupo06.sistemapedidos.controller;

import com.grupo06.sistemapedidos.annotations.SwaggerApiResponses;
import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.exception.APIExceptionHandler;
import com.grupo06.sistemapedidos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody; 
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Controlador REST para gestionar usuarios en el sistema de pedidos.
 * Proporciona endpoints para registrar, autenticar, obtener, actualizar y eliminar usuarios.
 * 
 * {@link RestController} Indica que esta clase es un controlador REST que manejará solicitudes HTTP.
 * {@link RequestMapping} Define la ruta base para todos los endpoints de este controlador.
 * {@link Tag} Documentación de OpenAPI para este controlador.
 * {@link ResponseStatus} Define el código de estado HTTP para las respuestas.
 * 
 * Swagger:
 * {@link SwaggerApiResponses} Anotación personalizada para definir respuestas de API.
 * {@link Operation} Documentación de OpenAPI para cada operación del controlador.
 * {@link PreAuthorize} Anotación para restringir el acceso a ciertos endpoints según roles.
 * 
 * Errores:
 * {@link APIExceptionHandler} Manejo de excepciones personalizadas para errores de API.
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "Usuario", description = "Controlador para gestionar usuarios")
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
     */
    @PostMapping("auth/register")
    @SwaggerApiResponses
    @Operation(summary = "Registrar un nuevo usuario", description = "Permite registrar un usuario sin necesidad de autenticación.")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UsuarioDTO> registerUser(@RequestBody UsuarioDTO userDTO) {
        return ResponseEntity.ok(userService.userRegistry(userDTO));
    }

    /**
     * Login de usuario
     * 
     * @param usuarioDTO Datos del usuario
     * @return Respuesta con el usuario autenticado, junto con el token JWT
     */
    @PostMapping("auth/login")
    @SwaggerApiResponses
    @Operation(summary = "Login de usuario", description = "Permite el inicio de sesión de un usuario existente.")
    public ResponseEntity<UsuarioDTO> userLogin(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(userService.userLogin(usuarioDTO));
    }

    /**
     * Obtener todos los usuarios registrados
     *
     * @return Lista de usuarios registrados
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")  
    @SwaggerApiResponses
    @Operation(summary = "Obtener todos los usuarios", description = "Este endpoint requiere autenticación JWT.")
    public ResponseEntity<List<UsuarioDTO>> getAllUsers() {
        List<UsuarioDTO> usersDTO = userService.getAllRegisteredUsers();
        return ResponseEntity.ok(usersDTO);
    }

    /**
     * Obtener todos los usuarios registrados con la id especificada
     *
     * @param ids Lista de IDs de usuarios a obtener
     * @return Lista de usuarios encontrados
     */
    @GetMapping("/all/list")
    @PreAuthorize("hasRole('ADMIN')") 
    @SwaggerApiResponses
    @Operation(summary = "Obtener todos los usuarios", description = "Este endpoint requiere autenticación JWT.")
    public ResponseEntity<List<UsuarioDTO>> getAllUsers(@RequestParam List<Integer> ids) {
        return ResponseEntity.ok(userService.getAllUsers(ids));
    }

    /**
     * Obtener un usuario por su ID
     *
     * @param id ID del usuario
     * @return UsuarioDTO con los datos del usuario
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") 
    @SwaggerApiResponses
    @Operation(summary = "Obtener un usuario por ID", description = "Este endpoint requiere autenticación JWT.")
    public ResponseEntity<UsuarioDTO> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Obtener un usuario por su email
     * 
     * @param email Email del usuario
     * @return UsuarioDTO con los datos del usuario
     */
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    @SwaggerApiResponses
    @Operation(summary = "Obtener un usuario por email", description = "Este endpoint requiere autenticación JWT.")
    public UsuarioDTO getMethodName(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    /**
     * Actualizar un usuario logeado actualmente
     * 
     * @param entity DTO con los datos del usuario a actualizar
     * @return UsuarioDTO con los datos del usuario actualizado
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") 
    @SwaggerApiResponses
    @Operation(summary = "Actualizar un usuario por ID", description = "Este endpoint requiere autenticación JWT.")
    public UsuarioDTO putUserById(@PathVariable Integer id, @RequestBody UsuarioDTO entity) {
        return userService.putUserById(id, entity);
    }

    /**
     * Actualizar un usuario por su email
     * 
     * @param email Email del usuario
     * @param entity DTO con los datos del usuario a actualizar
     * @return UsuarioDTO con los datos del usuario actualizado
     */
    @PutMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')") 
    @SwaggerApiResponses
    @Operation(summary = "Actualizar un usuario por email", description = "Este endpoint requiere autenticación JWT.")
    public UsuarioDTO putUserByEmail(@PathVariable String email, @RequestBody UsuarioDTO entity) {
        return userService.putUserByEmail(email, entity);
    }
    
    /**
     * Eliminar un usuario por su ID
     *
     * @param id ID del usuario
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") 
    @SwaggerApiResponses
    @Operation(summary = "Eliminar un usuario por ID", description = "Este endpoint requiere autenticación JWT.")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Eliminar un usuario por su email
     * 
     * @param email Email del usuario
     * @return Respuesta HTTP sin contenido
     */
    @DeleteMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')") 
    @SwaggerApiResponses
    @Operation(summary = "Eliminar un usuario por su email", description = "Este endpoint requiere autenticación JWT.")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }
}