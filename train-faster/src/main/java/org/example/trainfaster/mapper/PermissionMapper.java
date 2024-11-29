package org.example.trainfaster.mapper;

import org.example.trainfaster.DTO.PermissionDTO;
import org.example.trainfaster.model.Permission;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMapper {
    PermissionMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(PermissionMapper.class);

    PermissionDTO permissionToPermissionDTO(Permission permission);
    Permission permissionDTOToPermission(PermissionDTO permissionDTO);
}
