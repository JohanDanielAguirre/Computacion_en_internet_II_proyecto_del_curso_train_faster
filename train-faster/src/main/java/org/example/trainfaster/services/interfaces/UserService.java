package org.example.trainfaster.services.interfaces;


import org.example.trainfaster.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    public void saveUser(Users user);
    public Optional<Users> findUserByName(String username);

    Optional<Users> findUserById(Integer id);

    public Users authenticationUser(String username, String password);

    void deleteUserFromAllRoles(Integer roleId);

    List<Users> findAllUsers();

    public Users createUser(Users user);

    void deleteUserById(Integer id);

    void updateUser(Users user);
}
