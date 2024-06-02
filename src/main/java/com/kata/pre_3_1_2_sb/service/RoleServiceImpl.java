package com.kata.pre_3_1_2_sb.service;

import com.kata.pre_3_1_2_sb.api.response.RoleResponse;
import com.kata.pre_3_1_2_sb.dao.RoleDao;
import com.kata.pre_3_1_2_sb.mapper.RoleMapper;
import com.kata.pre_3_1_2_sb.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleDao roleDao;
    private final RoleMapper roleMapper;
    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> roleList = roleDao.getAllRoles();
        return roleMapper.toRoleResponseList(roleList);
    }

    @Override
    @Transactional
    public void save(Role role) {
         roleDao.save(role);
    }

    @Override
    public Set<Role> findRoles(List<Integer> roleList) {
        return roleDao.findRoles(roleList);
    }
}
