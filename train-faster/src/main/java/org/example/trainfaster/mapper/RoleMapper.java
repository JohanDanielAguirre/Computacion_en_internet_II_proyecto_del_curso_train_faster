package org.example.trainfaster.mapper;

import org.example.trainfaster.DTO.RoleDTO;
import org.example.trainfaster.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "permissions", ignore = true) // Manejaremos los permisos de forma separada
    Role roleDTOToRole(RoleDTO roleDTO);

    @Mapping(source = "permissions", target = "permissionIds", ignore = true) // Asumimos que solo transformaremos los IDs
    RoleDTO roleToRoleDTO(Role role);
}