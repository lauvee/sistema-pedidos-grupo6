package com.grupo06.sistemapedidos.controller;

import org.springframework.web.bind.annotation.RestController;
import com.grupo06.sistemapedidos.annotations.SwaggerApiResponses;
import com.grupo06.sistemapedidos.dto.RolesDTO;
import com.grupo06.sistemapedidos.enums.RoleEnum;
import com.grupo06.sistemapedidos.service.RoleService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



/**
 * Controlador REST para manejar las operaciones relacionadas con los roles.
 * Permite crear y eliminar roles en el sistema.
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
     * @param rol Objeto Roles que representa el rol a crear, deve ser unico y de tipo RoleEnum
     * RoleEnum puede ser ADMIN o USER o GUEST, esto es útil para solo tener roles predefinidos en el código.
     * @return El rol creado.
     */
    @PostMapping()
    @SwaggerApiResponses
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
    public RolesDTO getRoleByName(@PathVariable String name) {
        return roleService.getRoleByName(RoleEnum.valueOf(name.toUpperCase()));
    }

    /**
    * Obtiene todos los roles del sistema.
    *
    * @param param
    * @return
    */
    @GetMapping("/all")
    @SwaggerApiResponses
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putRoleByName(@PathVariable String name, @RequestBody RolesDTO entity) {
        roleService.updateRoleByName(name, entity);
    }

    /**
     * Elimina un rol del sistema por su ID.
     * 
     * @param id
     */
    @DeleteMapping("/{id}")
    @SwaggerApiResponses
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
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void delRole(@PathVariable String name) {
        roleService.deleteRoleByName(RoleEnum.valueOf(name.toUpperCase()));
    }
}
