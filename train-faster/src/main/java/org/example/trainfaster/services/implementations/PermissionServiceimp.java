package org.example.trainfaster.services.implementations;

import org.example.trainfaster.model.Permission;
import org.example.trainfaster.model.Role;
import org.example.trainfaster.repositories.PermissionRepository;
import org.example.trainfaster.repositories.RoleRepository;
import org.example.trainfaster.services.interfaces.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceimp implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void assignPermissionToRole(Integer roleId, String permissionName) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        Permission permission = permissionRepository.findByName(permissionName).orElseThrow(() -> new RuntimeException("Permission not found"));

        if (role.getPermissions() == null) {
            role.setPermissions(new ArrayList<>());
        }

        if (role.getPermissions().stream().noneMatch(p -> p.getName().equals(permission.getName()))) {
            role.getPermissions().add(permission);
            if (permission.getRoles() == null) {
                permission.setRoles(new ArrayList<>());
            }
            permission.getRoles().add(role);
            roleRepository.save(role);
        }
    }
    @Override
    public void assignAllPermissionsToRole(Integer roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow();
        List<Permission> permissions = permissionRepository.findAll();
        role.getPermissions().addAll(permissions);
        roleRepository.save(role);
    }

    @Override
    public void assignPermissionToAllRoles(String permissionName) {
        Permission permission = permissionRepository.findByName(permissionName).orElseThrow();
        List<Role> roles = roleRepository.findAll();
        for (Role role : roles) {
            role.getPermissions().add(permission);
            roleRepository.save(role);
        }
    }

    @Override
    public void deletePermissionFromRole(Integer roleId, String permissionName) {
        Role role = roleRepository.findById(roleId).orElseThrow();
        Permission permission = permissionRepository.findByName(permissionName).orElseThrow();
        role.getPermissions().remove(permission);
        roleRepository.save(role);
    }

    @Override
    public void deleteAllPermissionsFromRole(Integer roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow();
        role.getPermissions().clear();
        roleRepository.save(role);
    }



    @Override
    public void deletePermissionFromAllRoles(String permissionName) {
        Permission permission = permissionRepository.findByName(permissionName).orElseThrow();
        List<Role> roles = roleRepository.findAll();
        for (Role role : roles) {
            role.getPermissions().remove(permission);
            roleRepository.save(role);
        }
    }

    @Override
    public List<Permission> findAllPermissions(){
        return permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> findById(Integer permissionId){
        return permissionRepository.findById(permissionId);
    }
}
