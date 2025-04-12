package com.grupo06.sistemapedidos.controller;

import org.springframework.web.bind.annotation.RestController;

import com.grupo06.sistemapedidos.model.Roles;
import com.grupo06.sistemapedidos.service.RoleService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/role")
@RestController
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping()
    public Roles postMethodName(@RequestBody Roles entity) {
        return roleService.createRole(entity);
    }

    @DeleteMapping("/{id}")
    public void delRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
    }
    
}
