package com.kata.pre_3_1_2_sb.api.request;

import com.kata.pre_3_1_2_sb.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRequest {
    private Long id;

    private String name;

    private int age;

    private String job;

    private String email;

    private String password;

    private Set<Role> roles;
}
