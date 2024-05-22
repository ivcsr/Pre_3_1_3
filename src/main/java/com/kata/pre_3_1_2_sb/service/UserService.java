package com.kata.pre_3_1_2_sb.service;

import com.kata.pre_3_1_2_sb.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getUsers();
    User getUserById(UUID id);

    void updateUser(User user);

    void removeUserById(UUID id);

    void save(User user);

    User getUser(UserDetails userDetails);
}
