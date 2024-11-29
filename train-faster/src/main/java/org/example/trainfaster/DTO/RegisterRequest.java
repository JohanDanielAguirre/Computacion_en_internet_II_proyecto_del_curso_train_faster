package org.example.trainfaster.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String lastName;
    private List<String> roles = new ArrayList<>();
}
