package org.example.trainfaster.controllers;

import org.example.trainfaster.DTO.AssignPermissionRequest;
import org.example.trainfaster.services.interfaces.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> assignPermission(@RequestBody AssignPermissionRequest request) {
        permissionService.assignPermissionToRole(request.getRoleId(), request.getPermissionName());
        return ResponseEntity.ok().build();
    }
}
