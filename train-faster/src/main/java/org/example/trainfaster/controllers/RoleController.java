package org.example.trainfaster.controllers;

import org.example.trainfaster.DTO.RoleDTO;
import org.example.trainfaster.mapper.RoleMapper;
import org.example.trainfaster.model.Role;
import org.example.trainfaster.model.Permission;
import org.example.trainfaster.services.implementations.RoleServiceImp;
import org.example.trainfaster.services.interfaces.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleServiceImp roleService;

    @Autowired
    private PermissionService permissionService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/form")
    public ResponseEntity<List<Permission>> createRoleForm() {
        List<Permission> permissions = permissionService.findAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        Role role = RoleMapper.INSTANCE.roleDTOToRole(roleDTO);

        // Guardar el rol
        roleService.save(role);

        // Asignar permisos al rol
        List<Permission> permissionObjects = roleDTO.getPermissionIds().stream()
                .map(permissionId -> permissionService.findById(permissionId)
                        .orElseThrow(() -> new RuntimeException("Permission not found: " + permissionId)))
                .collect(Collectors.toList());

        for (Permission perm : permissionObjects) {
            permissionService.assignPermissionToRole(role.getId(), perm.getName());
        }

        return ResponseEntity.ok(RoleMapper.INSTANCE.roleToRoleDTO(role));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<RoleDTO>> listRoles() {
        List<Role> roles = roleService.findAllRoles();
        List<RoleDTO> roleDTOs = roles.stream()
                .map(RoleMapper.INSTANCE::roleToRoleDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roleDTOs);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deleteRole(@RequestParam Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }
}