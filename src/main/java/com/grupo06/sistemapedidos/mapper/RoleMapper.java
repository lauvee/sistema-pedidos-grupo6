package com.grupo06.sistemapedidos.mapper;

import org.springframework.stereotype.Component;

import com.grupo06.sistemapedidos.dto.RolesDTO;
import com.grupo06.sistemapedidos.model.Roles;

/**
 * RoleMapper es una clase que se encarga de convertir entre la entidad Roles y el DTO RolesDTO.
 * Está marcada con la anotación @Component para que Spring la detecte como un componente y
 */
@Component
public class RoleMapper {

    public RolesDTO toDto(Roles role) {
        if (role == null) {
            return null;
        }

        RolesDTO dto = new RolesDTO();
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());

        return dto;
    }

    public Roles toEntity(RolesDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Roles(
            dto.getName(),
            dto.getDescription()
        );
    }
}
