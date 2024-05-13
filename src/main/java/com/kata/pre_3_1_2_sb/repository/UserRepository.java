package com.kata.pre_3_1_2_sb.repository;

import com.kata.pre_3_1_2_sb.domain.UserDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserDb, UUID> {

    Optional<UserDb> findByEmail(String username);
}
