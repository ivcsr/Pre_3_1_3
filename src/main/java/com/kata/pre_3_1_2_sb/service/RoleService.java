package com.kata.pre_3_1_2_sb.service;

import com.kata.pre_3_1_2_sb.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();

    void save(Role role);

    Set<Role> findRoles(List<Integer> roleList);
}
