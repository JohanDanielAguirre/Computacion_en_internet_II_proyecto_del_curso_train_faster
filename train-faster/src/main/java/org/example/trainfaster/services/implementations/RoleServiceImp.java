package org.example.trainfaster.services.implementations;

import org.example.trainfaster.model.Role;

import org.example.trainfaster.model.Users;
import org.example.trainfaster.repositories.RoleRepository;
import org.example.trainfaster.repositories.UserRepository;

import org.example.trainfaster.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    private UserServiceImp userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void save(Role role){
        roleRepository.save(role);
    }


    @Override
    public void assignRoleToUser(String username, Integer roleId) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if(user.getRoles() == null){
            user.setRoles(new ArrayList<>());
        }
        if(user.getRoles().stream().noneMatch(r -> r.getId().equals(role.getId()))){
            user.getRoles().add(role);
            if (role.getUsers() == null){
                role.setUsers(new ArrayList<>());
            }
        }
        role.getUsers().add(user);
        userRepository.save(user);
    }

    @Override
    public void assignAllRolesToUser(String username) {
        Users user = userRepository.findByUsername(username).
                orElseThrow(() -> new RuntimeException("User not found"));
        List<Role> roles = roleRepository.findAll();
        user.getRoles().addAll(roles);
    }


    @Override
    public void assignRoleToAllUsers(Integer roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        List<Users> users = userRepository.findAll();
        for(Users user : users){
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteRoleFromUser(String username, Integer roleId) {
        Users user = userService.findUserByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().remove(role);
        userRepository.save(user);
    }


    @Override
    public void deleteAllRolesFromUser(String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getRoles().clear();
        userRepository.save(user);
    }


    @Override
    public void deleteRoleFromAllUsers(Integer roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        List<Users> users = userRepository.findAll();
        for(Users user: users){
            user.getRoles().remove(role);
            userRepository.save(user);
        }

    }

    @Override
    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Integer roleId){
        return roleRepository.findById(roleId);
    }

    @Override
    public void deleteRole(Integer roleId){
        deleteRoleFromAllUsers(roleId);
        roleRepository.deleteById(roleId);
    }
}
