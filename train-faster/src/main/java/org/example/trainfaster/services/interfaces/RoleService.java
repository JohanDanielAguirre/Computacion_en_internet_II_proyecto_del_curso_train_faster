package org.example.trainfaster.services.interfaces;

import org.example.trainfaster.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoleService {

    public void save(Role role);

    public void assignRoleToUser(String username, Integer roleId);

    public void assignAllRolesToUser(String username);
    public void assignRoleToAllUsers(Integer roleId);

    public void deleteRoleFromUser(String username, Integer roleId);
    public void deleteAllRolesFromUser(String username);

    public void deleteRoleFromAllUsers(Integer roleId);

    List<Role> findAllRoles();

    Optional<Role> findById(Integer roleId);

    void deleteRole(Integer roleId);
}
