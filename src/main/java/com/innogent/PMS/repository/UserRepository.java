package com.innogent.PMS.repository;

import com.innogent.PMS.entities.Role;
import com.innogent.PMS.entities.User;
import com.innogent.PMS.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}

