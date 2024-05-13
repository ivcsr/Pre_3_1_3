package com.kata.pre_3_1_2_sb.service;

import com.kata.pre_3_1_2_sb.domain.RoleDb;
import com.kata.pre_3_1_2_sb.domain.UserDb;
import com.kata.pre_3_1_2_sb.exception.NotFoundException;
import com.kata.pre_3_1_2_sb.repository.RoleRepository;
import com.kata.pre_3_1_2_sb.repository.UserRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public List<UserDb> getUsers() {
        List<UserDb> userList = userRepository.findAll();

        if (userList.isEmpty()) {
            throw new NotFoundException("Not found users");
        }

        return userList;
    }

    @Override
    public UserDb getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found user by id " + id));
    }

    @Override
    @Transactional
    public void saveUser(UserDb newUser) {
        assignRoleToUser(newUser);
        encodeAndSetPassword(newUser);
        userRepository.save(newUser);
    }

    private void assignRoleToUser(UserDb user) {
        RoleDb roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NotFoundException("Role not found"));
        user.setRoles(Collections.singletonList(roleUser));
    }

    private void encodeAndSetPassword(UserDb user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
    }

    @Override
    @Transactional
    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(UserDb updatableUser) {
        userRepository.save(updatableUser);
    }

    @Override
    public UserDb getUser(UserDetails userDetails) {
        String email = userDetails.getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Not found user by email " + email));
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDb userDb = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("Not found user by name " + username));

        return new org.springframework.security.core.userdetails.User(userDb.getEmail(),
                userDb.getPassword(), mapUserRolesToGrandAuthorities(userDb.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapUserRolesToGrandAuthorities(Collection<RoleDb> roles) {
        return roles.stream().map(roleDb -> new SimpleGrantedAuthority(roleDb.name)).toList();
    }
}
