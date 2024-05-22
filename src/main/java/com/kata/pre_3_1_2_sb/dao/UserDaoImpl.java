package com.kata.pre_3_1_2_sb.dao;

import com.kata.pre_3_1_2_sb.exception.NotFoundException;
import com.kata.pre_3_1_2_sb.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User newUser) {
        entityManager.persist(newUser);
    }

    @Override
    public List<User> getUsers() {
        List<User> userList = entityManager.createQuery("select u from User u", User.class).getResultList();

        if (userList.isEmpty()) {
            throw new NotFoundException("Not found users");
        }

        return userList;
    }

    @Override
    public User getUserById(UUID id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }

    @Override
    public void update(User updatableUser) {
        entityManager.merge(updatableUser);
    }

    @Override
    public void removeById(UUID id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public User findByEmail(String email) {
        Query query = entityManager.createQuery("select u from User u where u.email=:email", User.class);
        query.setParameter("email", email);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException("User not found");
        }
    }
}
