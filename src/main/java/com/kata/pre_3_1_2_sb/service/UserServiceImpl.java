package com.kata.pre_3_1_2_sb.service;

import com.kata.pre_3_1_2_sb.api.request.UserRequest;
import com.kata.pre_3_1_2_sb.api.response.UserResponse;
import com.kata.pre_3_1_2_sb.dao.UserDao;
import com.kata.pre_3_1_2_sb.mapper.UserMapper;
import com.kata.pre_3_1_2_sb.model.Role;
import com.kata.pre_3_1_2_sb.model.User;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void save(UserRequest userRequest) {
        User newUser = userMapper.toUser(userRequest);
        String encodePassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodePassword);
        userDao.save(newUser);
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> userList = userDao.getUsers();
        return userMapper.toUserResponseList(userList);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userDao.getUserById(id);
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public void updateUser(UserRequest userRequest) {
        User updatableUser = userMapper.toUser(userRequest);
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
    public void removeUserById(Long id) {
        userDao.removeById(id);
    }

    @Override
    public UserResponse getUser(UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userDao.findByEmail(email);
        return userMapper.toUserResponse(user);
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
