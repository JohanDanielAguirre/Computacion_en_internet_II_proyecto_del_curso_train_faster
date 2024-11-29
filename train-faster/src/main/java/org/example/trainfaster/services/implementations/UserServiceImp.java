package org.example.trainfaster.services.implementations;


import org.example.trainfaster.model.Role;
import org.example.trainfaster.model.Users;
import org.example.trainfaster.repositories.RoleRepository;
import org.example.trainfaster.repositories.UserRepository;
import org.example.trainfaster.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userrepository;

    @Autowired
    private RoleRepository roleRepository;

    public Users createUser(Users user) {
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setName(user.getName());
        user.setLastName(user.getLastName());
        return userrepository.save(user);
    }

    @Override
    public void saveUser(Users user) {
        userrepository.save(user);
    }

    @Override
    public Optional<Users> findUserByName(String username) {
        return userrepository.findByUsername(username);
    }

    @Override
    public Optional<Users> findUserById(Integer id){
        return userrepository.findById(id);
    }


    @Override
    public Users authenticationUser(String username, String password) {
        Optional<Users> userOptional = userrepository.findByUsername(username);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void deleteUserFromAllRoles(Integer roleId) {
        Users user = userrepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Role> roles =roleRepository.findAll();
        for(Role role: roles){
            role.getUsers().remove(user);
            roleRepository.save(role);
        }

    }

    @Override
    public List<Users> findAllUsers() {
        return userrepository.findAll();
    }

    @Override
    public void deleteUserById(Integer id){
        deleteUserFromAllRoles(id);
        userrepository.deleteById(id);}

    @Override
    public void updateUser(Users user) {
        userrepository.save(user);
    }
}


