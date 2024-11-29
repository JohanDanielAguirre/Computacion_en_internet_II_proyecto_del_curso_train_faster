package org.example.trainfaster.DTO;

import lombok.Data;

@Data
public class AssignPermissionRequest {
    private Integer roleId;
    private String permissionName;
}
