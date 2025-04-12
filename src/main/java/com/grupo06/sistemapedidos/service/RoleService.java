package com.grupo06.sistemapedidos.service;

import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.model.Roles;
import com.grupo06.sistemapedidos.repository.RoleRepository;

/**
 * Clase de servicio para manejar la lógica de negocio relacionada con los roles.
 * Proporciona métodos para crear y eliminar roles.
 * 
* @Service indica que esta clase es un servicio de Spring y permite la inyección de dependencias.
 */
@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Roles createRole(Roles role) {
        return roleRepository.save(role);
    }

    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }
}
