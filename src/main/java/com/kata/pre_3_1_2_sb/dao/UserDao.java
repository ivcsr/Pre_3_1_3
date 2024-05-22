package com.kata.pre_3_1_2_sb.dao;

import com.kata.pre_3_1_2_sb.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {

    List<User> getUsers();

    User getUserById(UUID id);

    void update(User updatableUser);

    void removeById(UUID id);

    void save(User newUser);

    User findByEmail(String username);
}
