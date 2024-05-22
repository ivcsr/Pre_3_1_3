package com.kata.pre_3_1_2_sb.dao;

import com.kata.pre_3_1_2_sb.exception.NotFoundException;
import com.kata.pre_3_1_2_sb.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public Set<Role> findRoles(List<Integer> roleList) {
        TypedQuery<Role> q = entityManager.createQuery("select r from Role r where r.id in :role", Role.class);
        q.setParameter("role", roleList);
        return new HashSet<>(q.getResultList());
    }
}
