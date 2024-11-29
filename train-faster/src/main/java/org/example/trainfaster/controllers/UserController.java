package org.example.trainfaster.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.trainfaster.DTO.UsersDTO;
import org.example.trainfaster.mapper.UsersMapper;
import org.example.trainfaster.model.Role;
import org.example.trainfaster.model.Users;
import org.example.trainfaster.services.interfaces.RoleService;
import org.example.trainfaster.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UsersMapper usersMapper;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO userDTO) {
        Users user = usersMapper.usersDTOToUsers(userDTO);
        Users createdUser = userService.createUser(user);
        return ResponseEntity.ok(usersMapper.usersToUsersDTO(createdUser));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<UsersDTO>> home() {
        List<Users> users = userService.findAllUsers();
        List<UsersDTO> userDTOs = users.stream()
                .map(usersMapper::usersToUsersDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(params = {"id"})
    public ResponseEntity<?> showEditUserForm(@PathVariable Integer id) {
        Optional<Users> user = userService.findUserById(id);
        if (user.isPresent()) {
            UsersDTO userDTO = usersMapper.usersToUsersDTO(user.get());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UsersDTO userDTO) {
        Users user = usersMapper.usersDTOToUsers(userDTO);
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(params = {"id"})
    public ResponseEntity<?> deleteUser(@RequestParam Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
