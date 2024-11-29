package org.example.trainfaster.DTO;


import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {
    private Integer id;
    private String name;
    private String description;
    private List<Integer> permissionIds;
    private List<Integer> userIds;
}
