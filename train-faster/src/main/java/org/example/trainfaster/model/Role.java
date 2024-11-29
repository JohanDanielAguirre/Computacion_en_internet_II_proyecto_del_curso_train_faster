package org.example.trainfaster.model;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role",schema = "public")
public class Role implements Serializable {

    @PostConstruct
    public void init(){
        Role role = new Role();
        role.setId(1);
        role.setName("name");
        role.setDescription("description");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            schema = "public",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<Users> users;
}
