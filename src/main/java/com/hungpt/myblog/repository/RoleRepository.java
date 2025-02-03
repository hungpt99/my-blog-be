package com.hungpt.myblog.repository;

import com.hungpt.myblog.entity.Role;
import com.hungpt.myblog.util.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(Constants.RoleEnum name);
}
