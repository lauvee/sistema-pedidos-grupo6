package com.grupo06.sistemapedidos.controller;

import org.springframework.web.bind.annotation.RestController;

import com.grupo06.sistemapedidos.model.Roles;
import com.grupo06.sistemapedidos.service.RoleService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador REST para manejar las operaciones relacionadas con los roles.
 * Permite crear y eliminar roles en el sistema.
 */
@RequestMapping("/role")
@RestController
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Crea un nuevo rol en el sistema.
     * 
     * @param rol Objeto Roles que representa el rol a crear, deve ser unico y de tipo RoleEnum
     * RoleEnum puede ser ADMIN o USER o GUEST, esto es útil para solo tener roles predefinidos en el código.
     * @return El rol creado.
     */
    @PostMapping()
    public Roles postMethodName(@RequestBody Roles rol) {
        return roleService.createRole(rol);
    }

    /**
     * Elimina un rol del sistema por su ID.
     * 
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
    }
    
}
