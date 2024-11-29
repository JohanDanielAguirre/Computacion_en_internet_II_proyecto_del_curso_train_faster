/*
package org.example.trainfaster.service;

import jakarta.transaction.Transactional;
import org.example.trainfaster.model.Role;
import org.example.trainfaster.model.Users;
import org.example.trainfaster.repositories.RoleRepository;
import org.example.trainfaster.repositories.UserRoleRepository;
import org.example.trainfaster.services.implementations.RoleServiceImp;
import org.example.trainfaster.services.implementations.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImp roleService;

    @Mock
    private UserServiceImp userService;

    @Mock
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    public void setUp() {
        // Crear lista de usuarios
        List<Users> usersList = new ArrayList<>();

        // Crear primer usuario
        Users user1 = new Users();
        user1.setUsername("mrzen");
        user1.setPassword("123");
        user1.setEmail("si@gmail.com");
        user1.setName("johan");
        user1.setLastName("aguirre");
        user1.setRoles(new ArrayList<>());

        // Crear segundo usuario
        Users user2 = new Users();
        user2.setUsername("johnDoe");
        user2.setPassword("456");
        user2.setEmail("john@gmail.com");
        user2.setName("John");
        user2.setLastName("Doe");
        user2.setRoles(new ArrayList<>());

        // Añadir usuarios a la lista de usuarios
        usersList.add(user1);
        usersList.add(user2);

        // Crear objetos Role
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");

        Role role1 = new Role();
        role1.setId(2);
        role1.setName("ROLE_ADMIN");

        Role role2 = new Role();
        role2.setId(3);
        role2.setName("ROLE_TRAVELLER");

        Role role3 = new Role();
        role3.setId(4);
        role3.setName("ROLE_STAFF");

        // Mock de findUserByName
        when(userService.findUserByName("mrzen")).thenReturn(Optional.of(user1));
        when(userService.findUserByName("johnDoe")).thenReturn(Optional.of(user2));

        // Mock de findAllUsers para obtener todos los usuarios
        when(userService.findAllUsers()).thenReturn(usersList);

        // Mock de roleRepository
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        when(roleRepository.findById(2)).thenReturn(Optional.of(role1));
        when(roleRepository.findById(3)).thenReturn(Optional.of(role2));
        when(roleRepository.findById(4)).thenReturn(Optional.of(role3));
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role, role1, role2, role3));

        // Lista para almacenar UserRole
        List<UserRole> userRoles = new ArrayList<>();

        // Mock de save para añadir un UserRole
        when(userRoleRepository.save(any(UserRole.class))).thenAnswer(invocation -> {
            UserRole savedUserRole = invocation.getArgument(0);
            userRoles.add(savedUserRole); // Añadir el UserRole a la lista
            return savedUserRole;
        });

        // Mock para findAll de UserRoleRepository
        when(userRoleRepository.findAll()).thenReturn(userRoles);

        // Mock para eliminar roles por usuario y rol
        doAnswer(invocation -> {
            Users mockUser = invocation.getArgument(0);
            Role mockRole = invocation.getArgument(1);
            userRoles.removeIf(userRole -> userRole.getUser().equals(mockUser) && userRole.getRole().equals(mockRole));
            return null;
        }).when(userRoleRepository).deleteByUserAndRole(any(Users.class), any(Role.class));

        // Mock para eliminar todos los roles de un usuario
        doAnswer(invocation -> {
            Users mockUser = invocation.getArgument(0);
            userRoles.removeIf(userRole -> userRole.getUser().equals(mockUser));
            return null;
        }).when(userRoleRepository).deleteByUser(any(Users.class));

        // Mock para eliminar un rol de todos los usuarios
        doAnswer(invocation -> {
            Role mockRole = invocation.getArgument(0);
            userRoles.removeIf(userRole -> userRole.getRole().equals(mockRole));
            return null;
        }).when(userRoleRepository).deleteByRole(any(Role.class));
    }


    @Test
    public void testAssignRoleToUser() {
        Users user = userService.findUserByName("mrzen")
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roleService.assignRoleToUser("mrzen",1);
        assertEquals(1, userRoleRepository.findAll().size());
        assertEquals(role, userRoleRepository.findAll().get(0).getRole());
        assertEquals(user, userRoleRepository.findAll().get(0).getUser());
        Role role2 = roleRepository.findById(3)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roleService.assignRoleToUser("mrzen",3);
        assertEquals(2, userRoleRepository.findAll().size());
        assertEquals(role2, userRoleRepository.findAll().get(1).getRole());
        assertEquals(user, userRoleRepository.findAll().get(1).getUser());
    }

    @Test
    public void testAssignAllRolesToUser() {
        roleService.assignAllRolesToUser("mrzen");
        assertEquals(4, userRoleRepository.findAll().size());
        assertEquals(1, userRoleRepository.findAll().get(0).getRole().getId());
        assertEquals(2, userRoleRepository.findAll().get(1).getRole().getId());
        assertEquals(3, userRoleRepository.findAll().get(2).getRole().getId());
    }

    @Test
    public void testAssignRoleToAllUsers() {
        roleService.assignRoleToAllUsers(1);
        assertEquals(2, userRoleRepository.findAll().size());
        assertEquals(1, userRoleRepository.findAll().get(0).getRole().getId());
        Users user = userService.findUserByName("mrzen")
                .orElseThrow(() -> new RuntimeException("User not found"));
        assertEquals(user, userRoleRepository.findAll().get(0).getUser());
    }

    @Test
    public void testDeleteRoleFromUser() {
        roleService.assignAllRolesToUser("mrzen");
        assertEquals(4, userRoleRepository.findAll().size());
        roleService.deleteRoleFromUser("mrzen", 1);
        assertEquals(3, userRoleRepository.findAll().size());
        roleService.deleteRoleFromUser("mrzen", 2);
        assertEquals(2, userRoleRepository.findAll().size());
        roleService.deleteRoleFromUser("mrzen", 3);
        assertEquals(1, userRoleRepository.findAll().size());
        roleService.deleteRoleFromUser("mrzen", 4);
        assertEquals(0, userRoleRepository.findAll().size());
    }

    @Test
    public void testDeleteAllRolesFromUser() {
        roleService.assignAllRolesToUser("mrzen");
        assertEquals(4, userRoleRepository.findAll().size());
        roleService.deleteAllRolesFromUser("mrzen");
        assertEquals(0, userRoleRepository.findAll().size());
    }

    @Test
    public void testDeleteRoleFromAllUsers() {
        roleService.assignRoleToAllUsers(1);
        assertEquals(2, userRoleRepository.findAll().size());
        roleService.deleteRoleFromAllUsers(1);
        assertEquals(0, userRoleRepository.findAll().size());
    }
}
 */
