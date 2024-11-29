package org.example.trainfaster.repositories;


import org.example.trainfaster.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findById(Integer Id);

    Optional<Role> findByName(String roleName);
}
