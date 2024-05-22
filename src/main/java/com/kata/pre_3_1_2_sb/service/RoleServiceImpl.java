package com.kata.pre_3_1_2_sb.service;

import com.kata.pre_3_1_2_sb.dao.RoleDao;
import com.kata.pre_3_1_2_sb.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleDao roleDao;
    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    @Transactional
    public void save(Role role) {
         roleDao.save(role);
    }
}
