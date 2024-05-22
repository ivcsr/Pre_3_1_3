package com.kata.pre_3_1_2_sb.service;

import com.kata.pre_3_1_2_sb.dao.UserDao;
import com.kata.pre_3_1_2_sb.model.Role;
import com.kata.pre_3_1_2_sb.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void save(User newUser) {
        String encodePassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodePassword);
        userDao.save(newUser);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public User getUserById(UUID id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public void updateUser(User updatableUser) {
        User originalUser = userDao.getUserById(updatableUser.getId());
        String originalPassword = originalUser.getPassword();

        if (!updatableUser.getPassword().equals(originalPassword)) {
            encodeAndSetPassword(updatableUser);
        }

        userDao.update(updatableUser);
    }

    private void encodeAndSetPassword(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
    }

    @Override
    @Transactional
    public void removeUserById(UUID id) {
        userDao.removeById(id);
    }

    @Override
    public User getUser(UserDetails userDetails) {
        String email = userDetails.getUsername();
        return userDao.findByEmail(email);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByEmail(username);

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), mapUserRolesToGrandAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapUserRolesToGrandAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name)).toList();
    }
}
