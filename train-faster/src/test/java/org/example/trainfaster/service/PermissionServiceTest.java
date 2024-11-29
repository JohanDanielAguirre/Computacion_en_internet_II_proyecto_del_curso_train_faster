package org.example.trainfaster.service;

import jakarta.transaction.Transactional;
import org.example.trainfaster.model.Permission;
import org.example.trainfaster.model.Role;
import org.example.trainfaster.repositories.PermissionRepository;
import org.example.trainfaster.repositories.RoleRepository;
import org.example.trainfaster.services.implementations.PermissionServiceimp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private PermissionServiceimp permissionService;

    private Role role;
    private Permission permissionRead;
    private Permission permissionWrite;
    private List<Role> roles;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        role = new Role();
        role.setId(1);
        role.setPermissions(new ArrayList<>());

        permissionRead = new Permission();
        permissionRead.setName("READ");

        permissionWrite = new Permission();
        permissionWrite.setName("WRITE");

        roles = new ArrayList<>();
        roles.add(role);
    }

    /*
    @Test
    public void testAssignPermissionToRole() {
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        when(permissionRepository.findByName("READ")).thenReturn(Optional.of(permissionRead));

        permissionService.assignPermissionToRole(role.getId(), permissionRead.getName());

        assertEquals(1, role.getPermissions().size());
        assertEquals("READ", role.getPermissions().get(0).getName());

        when(permissionRepository.findByName("WRITE")).thenReturn(Optional.of(permissionWrite));

        permissionService.assignPermissionToRole(role.getId(), permissionWrite.getName());

        // Verifica que ahora haya dos permisos
        assertEquals(2, role.getPermissions().size());
        assertEquals("WRITE", role.getPermissions().get(1).getName());
    }

    @Test
    public void testAssignAllPermissionsToRole() {
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        List<Permission> allPermissions = new ArrayList<>();

        Permission permissionAdd = new Permission();
        permissionAdd.setName("ADD");

        Permission permissionDelete = new Permission();
        permissionDelete.setName("DELETE");

        Permission permissionUpdate = new Permission();
        permissionUpdate.setName("UPDATE");

        Permission permissionUpload = new Permission();
        permissionUpload.setName("UPLOAD");

        allPermissions.add(permissionRead);
        allPermissions.add(permissionWrite);
        allPermissions.add(permissionAdd);
        allPermissions.add(permissionDelete);
        allPermissions.add(permissionUpdate);
        allPermissions.add(permissionUpload);

        when(permissionRepository.findAll()).thenReturn(allPermissions);

        permissionService.assignAllPermissionsToRole(role.getId());

        assertEquals(6, role.getPermissions().size());
        assertEquals("READ", role.getPermissions().get(0).getName());
        assertEquals("WRITE", role.getPermissions().get(1).getName());
        assertEquals("ADD", role.getPermissions().get(2).getName());
        assertEquals("DELETE", role.getPermissions().get(3).getName());
        assertEquals("UPDATE", role.getPermissions().get(4).getName());
        assertEquals("UPLOAD", role.getPermissions().get(5).getName());
    }

    @Test
    public void testAssignPermissionToAllRoles() {
        when(permissionRepository.findByName("READ")).thenReturn(Optional.of(permissionRead));
        when(roleRepository.findAll()).thenReturn(roles);

        permissionService.assignPermissionToAllRoles(permissionRead.getName());

        for (Role role : roles) {
            assertEquals(1, role.getPermissions().size());
            assertEquals("READ", role.getPermissions().get(0).getName());
        }
    }

    @Test
    public void testDeletePermissionFromRole() {
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        when(permissionRepository.findByName("READ")).thenReturn(Optional.of(permissionRead));

        permissionService.assignPermissionToRole(role.getId(), permissionRead.getName());

        assertEquals(1, role.getPermissions().size());

        permissionService.deletePermissionFromRole(role.getId(), permissionRead.getName());

        assertEquals(0, role.getPermissions().size());
    }

    @Test
    public void testDeleteAllPermissionsFromRole() {
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        List<Permission> allPermissions = new ArrayList<>();

        Permission permissionAdd = new Permission();
        permissionAdd.setName("ADD");

        Permission permissionDelete = new Permission();
        permissionDelete.setName("DELETE");

        Permission permissionUpdate = new Permission();
        permissionUpdate.setName("UPDATE");

        Permission permissionUpload = new Permission();
        permissionUpload.setName("UPLOAD");

        allPermissions.add(permissionRead);
        allPermissions.add(permissionWrite);
        allPermissions.add(permissionAdd);
        allPermissions.add(permissionDelete);
        allPermissions.add(permissionUpdate);
        allPermissions.add(permissionUpload);

        when(permissionRepository.findAll()).thenReturn(allPermissions);

        permissionService.assignAllPermissionsToRole(role.getId());

        assertEquals(6, role.getPermissions().size());

        permissionService.deleteAllPermissionsFromRole(role.getId());

        assertEquals(0, role.getPermissions().size());
    }

    @Test
    public void testDeletePermissionFromAllRoles() {
        when(permissionRepository.findByName("READ")).thenReturn(Optional.of(permissionRead));
        when(roleRepository.findAll()).thenReturn(roles);

        permissionService.assignPermissionToAllRoles(permissionRead.getName());

        for (Role role : roles) {
            assertEquals(1, role.getPermissions().size());
            assertEquals("READ", role.getPermissions().get(0).getName());
        }

        permissionService.deletePermissionFromAllRoles(permissionRead.getName());

        for (Role role : roles) {
            assertEquals(0, role.getPermissions().size());
        }
    }

     */
}
