package com.grupo06.sistemapedidos.mapper;

import com.grupo06.sistemapedidos.dto.RolesDTO;
import com.grupo06.sistemapedidos.model.Roles;

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
