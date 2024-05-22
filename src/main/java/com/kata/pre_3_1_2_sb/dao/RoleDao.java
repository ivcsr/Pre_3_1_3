package com.kata.pre_3_1_2_sb.dao;

import com.kata.pre_3_1_2_sb.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getAllRoles();

    void save(Role role);
}
