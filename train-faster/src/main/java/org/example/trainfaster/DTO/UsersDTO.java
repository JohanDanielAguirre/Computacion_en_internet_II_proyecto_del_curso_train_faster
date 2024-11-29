package org.example.trainfaster.DTO;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UsersDTO {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String lastName;
    private List<Integer> roleIds = new ArrayList<>();
    private List<Integer> ticketIds = new ArrayList<>();

}
