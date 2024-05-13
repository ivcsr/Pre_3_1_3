package com.kata.pre_3_1_2_sb.repository;

import com.kata.pre_3_1_2_sb.domain.RoleDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleDb, Integer> {
    Optional<RoleDb> findByName(String roleUser);
}
