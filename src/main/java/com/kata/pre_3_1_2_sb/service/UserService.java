package com.kata.pre_3_1_2_sb.service;

import com.kata.pre_3_1_2_sb.api.request.UserRequest;
import com.kata.pre_3_1_2_sb.api.response.UserResponse;
import com.kata.pre_3_1_2_sb.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponse> getUsers();
    UserResponse getUserById(Long id);

    void updateUser(UserRequest userRequest);

    void removeUserById(Long id);

    void save(UserRequest userRequest);

    UserResponse getUser(UserDetails userDetails);
}
