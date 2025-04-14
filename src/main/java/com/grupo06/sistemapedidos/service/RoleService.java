package com.grupo06.sistemapedidos.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.dto.RolesDTO;
import com.grupo06.sistemapedidos.enums.ApiError;
import com.grupo06.sistemapedidos.exception.RequestException;
import com.grupo06.sistemapedidos.mapper.RoleMapper;
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
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    /**
     * Método para obtener un rol por su ID.
     * 
     * @param id ID del rol a buscar.
     * @return RolesDTO DTO que representa el rol encontrado.
     */
    public RolesDTO getRoleById(Integer id) {
        try {
            Optional<Roles> role = roleRepository.findById(id);
            if(!role.isPresent()){
                throw new RequestException(ApiError.ROLE_NOT_FOUND);
            }
            return roleMapper.toDto(role.get());
        } catch (Exception e) {
             throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método para crear un nuevo rol.
     * 
     * @param role DTO que representa el rol a crear.
     * @return RolesDTO DTO que representa el rol creado.
     */
    public RolesDTO createRole(RolesDTO role) {
        try {
            Roles newRole = roleMapper.toEntity(role);
            newRole = roleRepository.save(newRole);
            return roleMapper.toDto(newRole);
        } catch (Exception e) {
             throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método para eliminar un rol por su ID.
     * 
     * @param id ID del rol a eliminar.
     */
    public void deleteRole(Integer id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
             throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }
}
