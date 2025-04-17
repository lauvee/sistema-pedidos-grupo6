package com.grupo06.sistemapedidos.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.grupo06.sistemapedidos.dto.RolesDTO;
import com.grupo06.sistemapedidos.enums.ApiError;
import com.grupo06.sistemapedidos.enums.RoleEnum;
import com.grupo06.sistemapedidos.exception.RequestException;
import com.grupo06.sistemapedidos.mapper.RoleMapper;
import com.grupo06.sistemapedidos.model.Roles;
import com.grupo06.sistemapedidos.repository.RoleRepository;
import jakarta.transaction.Transactional;

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
            if(!role.isPresent())
                throw new RequestException(ApiError.ROLE_NOT_FOUND);
            
            return roleMapper.toDto(role.get());
        }catch(RequestException e){
            throw e;
        } catch (Exception e) {
             throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    public RolesDTO getRoleByName(RoleEnum name) {
        try {
            Optional<Roles> role = roleRepository.findByName(name);
            if(!role.isPresent()){
                throw new RequestException(ApiError.ROLE_NOT_FOUND);
            }
            return roleMapper.toDto(role.get());
        } catch (Exception e) {
             throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método para obtener todos los roles.
     * 
     * @return Lista de RolesDTO que representan todos los roles.
     */
    public List<RolesDTO> getAllRoles() {
        try {
            List<Roles> roles = roleRepository.findAll();
            return roles.stream()
                    .map(roleMapper::toDto)
                    .toList();
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
            Optional<Roles> roleOptional = roleRepository.findByName(role.getName());
            //Ya existe por lo que no se puede crear
            if(roleOptional.isPresent())
                throw new RequestException(ApiError.ROLE_ALREADY_EXISTS);

            Roles newRole = roleRepository.save(roleMapper.toEntity(role));
            return roleMapper.toDto(newRole);
        }catch(RequestException e){
            throw e;
        } catch (Exception e) {
             throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método para actualizar un rol por su ID.
     * 
     * @param entity Objeto Roles que representa el rol a actualizar.
     * @return El rol actualizado.
     * @throws RequestException si el rol no existe o si ocurre un error interno.
     */
    public void updateRoleById(Integer id, RolesDTO entity) {
        try {
            Optional<Roles> role = roleRepository.findById(id);
            if(!role.isPresent())
                throw new RequestException(ApiError.ROLE_NOT_FOUND);

            Roles updatedRole = roleMapper.toEntity(entity);
            updatedRole.setId(role.get().getId());
            roleRepository.save(updatedRole);
        } catch (RequestException e) {
            throw e;
        } catch (Exception e) {
             throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método para actualizar un rol por su nombre.
     * 
     * @param name
     * @param entity
     * @return
     */
    public void updateRoleByName(String name, RolesDTO entity) {
        try {
            Optional<Roles> role = roleRepository.findByName(RoleEnum.valueOf(name.toUpperCase()));
            if(!role.isPresent())
                throw new RequestException(ApiError.ROLE_NOT_FOUND);

            Roles updatedRole = roleMapper.toEntity(entity);
            updatedRole.setId(role.get().getId());
            roleRepository.save(updatedRole);
        } catch (IllegalArgumentException e) {
            throw new RequestException(ApiError.ROLE_NOT_FOUND); 
        } catch (RequestException e) {
            throw e;
        } catch (Exception e) {
             throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método para eliminar un rol por su ID.
     * 
     * @param id ID del rol a eliminar.
     */
    public void deleteRoleById(Integer id) {
        try {
            Optional<Roles> role = roleRepository.findById(id);
            if(!role.isPresent())
                throw new RequestException(ApiError.ROLE_NOT_FOUND);

            roleRepository.deleteById(id);
        } catch(RequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método para eliminar un rol por su nombre.
     * 
     * @param role Nombre del rol a eliminar.{@link RoleEnum}
     */
    @Transactional
    public void deleteRoleByName(RoleEnum roleEnum) {
        try {
            Optional<Roles> role = roleRepository.findByName(roleEnum);
            if(!role.isPresent())
                throw new RequestException(ApiError.ROLE_NOT_FOUND);

            roleRepository.deleteByName(role.get().getName());
        }catch(RequestException e){
            throw e;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RequestException(ApiError.INTERNAL_SERVER_ERROR);
        }
    }
}