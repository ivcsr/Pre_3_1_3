package com.kata.pre_3_1_2_sb.dao;

import com.kata.pre_3_1_2_sb.exception.NotFoundException;
import com.kata.pre_3_1_2_sb.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
        List<Role> roleList = entityManager.createQuery("select r from Role r").getResultList();

        if (roleList.isEmpty()) {
            throw new NotFoundException("Not found users");
        }
        return roleList;
    }

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }
}
