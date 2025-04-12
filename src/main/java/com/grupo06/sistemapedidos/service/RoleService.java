package com.grupo06.sistemapedidos.service;

import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.model.Roles;
import com.grupo06.sistemapedidos.repository.RoleRepository;

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
