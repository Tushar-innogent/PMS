package com.innogent.PMS.repository;

import com.innogent.PMS.entities.Role;
import com.innogent.PMS.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(RoleName name);
}
