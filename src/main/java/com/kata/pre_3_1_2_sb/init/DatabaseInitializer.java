package com.kata.pre_3_1_2_sb.init;

import com.kata.pre_3_1_2_sb.model.Role;
import com.kata.pre_3_1_2_sb.model.User;
import com.kata.pre_3_1_2_sb.service.RoleService;
import com.kata.pre_3_1_2_sb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {
    private final UserService userService;
    private final RoleService roleService;

    @PostConstruct
    @Transactional
    public void initialize() {
        Role userRole = createAndSaveRole("ROLE_USER");
        User user = createUser("User", "IT", 22, "user@email.com", "pass", Set.of(userRole));
        userService.save(user);

        Role adminRole = createAndSaveRole("ROLE_ADMIN");
        User admin = createUser("Admin", "IT", 22, "admin@email.com", "pass", Set.of(adminRole, userRole));
        userService.save(admin);
    }

    private Role createAndSaveRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        roleService.save(role);
        return role;
    }

    private User createUser(String name, String job, int age, String email, String password, Set<Role> roles) {
        User user = new User();
        user.setName(name);
        user.setJob(job);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);
        return user;
    }
}
