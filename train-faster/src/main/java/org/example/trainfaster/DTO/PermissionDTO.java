package org.example.trainfaster.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PermissionDTO {
    private Integer id;
    private String name;
    private String resources;
    private List<Integer> roleIds;
}
