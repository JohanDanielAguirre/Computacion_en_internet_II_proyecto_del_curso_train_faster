package org.example.trainfaster.repositories;


import org.example.trainfaster.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    Optional<Permission> findById(Integer Id);

    Optional<Permission> findByName(String name);
}
