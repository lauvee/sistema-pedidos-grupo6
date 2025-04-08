package com.grupo06.sistemapedidos.controller;

import com.grupo06.sistemapedidos.dto.UsuarioDTO;
import com.grupo06.sistemapedidos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operaciones relacionadas con usuarios.
 */
@RestController // Usa RestController para que todas las respuestas sean JSON
@RequestMapping("/api/users") // Agrupa rutas RESTful
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios") // Anotación Swagger para etiquetar el controlador
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registra un nuevo usuario
     *
     * @param usuarioDTO datos del usuario
     * @return Usuario registrado
     */
    @PostMapping("/user")
    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario en el sistema con la información proporcionada.")
    public ResponseEntity<UsuarioDTO> userRegistry(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(userService.userRegistry(usuarioDTO));
    }

    /**
     * Login de usuario
     */
    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Permite el inicio de sesión de un usuario existente.")
    public ResponseEntity<UsuarioDTO> userLogin(@RequestBody UsuarioDTO usuarioDTO) throws Exception {
        return ResponseEntity.ok(userService.userLogin(usuarioDTO));
    }

    /**
     * Obtiene un usuario por su email.
     */
    @GetMapping("/user")
    @Operation(summary = "Obtener usuario por correo", description = "Obtiene la información de un usuario utilizando su correo electrónico.")
    public ResponseEntity<UsuarioDTO> getUser(@Parameter(description = "Correo electrónico del usuario") @RequestParam UsuarioDTO email) {
        return ResponseEntity.ok(userService.getUser(email));
    }

    /**
     * Obtiene todos los usuarios con los ids proporcionados.
     */
    @GetMapping("/users")
    @Operation(summary = "Obtener todos los usuarios", description = "Obtiene una lista de usuarios usando los IDs proporcionados.")
    public ResponseEntity<List<UsuarioDTO>> getAllUsers(@RequestParam List<Integer> ids) {
        return ResponseEntity.ok(userService.getAllUsers(ids));
    }

    /**
     * Elimina un usuario por su ID.
     */
    @DeleteMapping("/user/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema usando su ID.")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
