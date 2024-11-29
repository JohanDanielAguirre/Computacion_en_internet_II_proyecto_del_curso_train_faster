package org.example.trainfaster.services.interfaces;

import org.example.trainfaster.model.Permission;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PermissionService {

    public void assignPermissionToRole(Integer roleId, String permissionName);

    public void assignAllPermissionsToRole(Integer roleId);

    public void assignPermissionToAllRoles(String permissionName);

    public void deletePermissionFromRole(Integer roleId, String permissionName);

    public void deleteAllPermissionsFromRole(Integer roleId);

    public void deletePermissionFromAllRoles(String permissionName);

    List<Permission> findAllPermissions();

    Optional<Permission> findById(Integer permissionId);
}
