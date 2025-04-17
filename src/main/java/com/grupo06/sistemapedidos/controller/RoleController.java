package com.grupo06.sistemapedidos.controller;

import org.springframework.web.bind.annotation.RestController;
import com.grupo06.sistemapedidos.annotations.SwaggerApiResponses;
import com.grupo06.sistemapedidos.dto.RolesDTO;
import com.grupo06.sistemapedidos.enums.RoleEnum;
import com.grupo06.sistemapedidos.exception.APIExceptionHandler;
import com.grupo06.sistemapedidos.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Controlador REST para manejar las operaciones relacionadas con los roles.
 * Permite crear y eliminar roles en el sistema.
 * 
 * {@link RestController} Indica que esta clase es un controlador REST que manejará solicitudes HTTP.
 * {@link RequestMapping} Define la ruta base para todos los endpoints de este controlador.
 * {@link Tag} Documentación de OpenAPI para este controlador.
 * {@link ResponseStatus} Define el código de estado HTTP para las respuestas.
 * 
 * Swagger:
 * {@link SwaggerApiResponses} Anotación personalizada para definir respuestas de API.
 * {@link Operation} Documentación de OpenAPI para cada operación del controlador.
 * 
 * Errores:
 * {@link APIExceptionHandler} Manejo de excepciones personalizadas para errores de API.
 */
@RequestMapping("/api/role")
@RestController
@Tag(name = "Rol", description = "Controlador para gestionar roles")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Crea un nuevo rol en el sistema, no admite duplicados y deven ser de tipo RoleEnum.
     * 
     * @param rol Representa el rol a crear en el sistema, deve de estar creado anteriormente en el Enum {@link RoleEnum}.
     * RoleEnum puede ser ADMIN o USER o GUEST por el momento, esto es útil para solo tener roles predefinidos en el código.
     * @return El rol creado.
     */
    @PostMapping()
    @SwaggerApiResponses
    @Operation(summary = "Crear un nuevo rol", description = "Crea un nuevo rol en el sistema. Los roles deben ser únicos y de tipo RoleEnum, de momentos estan disponibles para crear ADMIN, USER, GUEST.")
    @ResponseStatus(HttpStatus.CREATED)
    public RolesDTO postRole(@RequestBody RolesDTO rol) {
        return roleService.createRole(rol);
    }

    /**
     * Obtiene un rol del sistema por su ID.
     * 
     * @param role ID del rol a buscar.
     * @return RolesDTO DTO que representa el rol encontrado.
     */
    @GetMapping("/{id}")
    @SwaggerApiResponses
    @Operation(summary = "Obtener un rol por su ID", description = "Devuelve un rol específico basado en su ID.")
    public RolesDTO getRoleById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }

    /**
     * Obtiene un rol del sistema por su nombre.
     * 
     * @param name Nombre del rol a buscar, debe ser de tipo RoleEnum.
     * @return RolesDTO DTO que representa el rol encontrado.
     */
    @GetMapping("/name/{name}")
    @SwaggerApiResponses
    @Operation(summary = "Obtener un rol por su nombre", description = "Devuelve un rol específico basado en su nombre.")
    public RolesDTO getRoleByName(@PathVariable String name) {
        return roleService.getRoleByName(RoleEnum.valueOf(name.toUpperCase()));
    }

    /**
    * Obtiene todos los roles del sistema.
    *
    * @param param Parámetro opcional para filtrar los roles.
    * @return Lista de RolesDTO que representan todos los roles en el sistema.
    */
    @GetMapping("/all")
    @SwaggerApiResponses
    @Operation(summary = "Obtener todos los roles", description = "Devuelve una lista de todos los roles.")
    public List<RolesDTO> getAllRoles() {
        return roleService.getAllRoles();
    }
    
    /**
     * Actualiza un rol del sistema por su ID.
     * 
     * @param id ID del rol a actualizar.
     * @param entity Objeto Roles que representa el rol a actualizar.
     * @return El rol actualizado.
     */
    @PutMapping("/{id}")
    @SwaggerApiResponses
    @Operation(summary = "Actualizar un rol por su ID", description = "Actualiza un rol específico basado en su ID.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putRoleById(@PathVariable Integer id, @RequestBody RolesDTO entity) {
        roleService.updateRoleById(id, entity);
    }

    /**
     * Actualiza un rol del sistema por su nombre.
     * 
     * @param entity Objeto Roles que representa el rol a actualizar.
     * @return El rol actualizado.
     */
    @PutMapping("/name/{name}")
    @SwaggerApiResponses
    @Operation(summary = "Actualizar un rol por su nombre", description = "Actualiza un rol específico basado en su nombre.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putRoleByName(@PathVariable String name, @RequestBody RolesDTO entity) {
        roleService.updateRoleByName(name, entity);
    }

    /**
     * Elimina un rol del sistema por su ID.
     * 
     * @param id ID del rol a eliminar.
     */
    @DeleteMapping("/{id}")
    @SwaggerApiResponses
    @Operation(summary = "Eliminar un rol por su ID", description = "Elimina un rol específico basado en su ID.")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void delRole(@PathVariable Integer id) {
        roleService.deleteRoleById(id);
    }

    /**
     * Elimina un rol del sistema por su nombre.
     * 
     * @param name Nombre del rol a eliminar, debe ser de tipo RoleEnum.
     */
    @DeleteMapping("/name/{name}")
    @SwaggerApiResponses
    @Operation(summary = "Eliminar un rol por su nombre", description = "Elimina un rol específico basado en su nombre.")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void delRole(@PathVariable String name) {
        roleService.deleteRoleByName(RoleEnum.valueOf(name.toUpperCase()));
    }
}
