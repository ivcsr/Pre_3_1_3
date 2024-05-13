package com.kata.pre_3_1_2_sb.service;

import com.kata.pre_3_1_2_sb.domain.UserDb;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDb> getUsers();
    UserDb getUserById(UUID id);

    void saveUser(UserDb newUser);

    void deleteUserById(UUID id);

    void updateUser(UserDb updatableUser);

    UserDb getUser(UserDetails userDetails);
}
