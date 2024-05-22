package com.kata.pre_3_1_2_sb.controller;

import com.kata.pre_3_1_2_sb.model.Role;
import com.kata.pre_3_1_2_sb.model.User;
import com.kata.pre_3_1_2_sb.service.RoleService;
import com.kata.pre_3_1_2_sb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/new")
    public String createNewUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roleList", roleService.getAllRoles());
        return "new";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam(value = "roles") Set<Role> roleList) {
        user.setRoles(roleList);
        userService.save(user);
        return "redirect:/admin";
    }
    @GetMapping()
    public String getAllUsers(Model model) {
        System.out.println("Я здесь");
        userService.getUsers().forEach(e -> System.out.println(e.getRoles()));
        model.addAttribute("users", userService.getUsers());
        return "admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable UUID id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roleList", roleService.getAllRoles());
        return "edit";
    }

    @PatchMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam(value = "roles") Set<Role> roleList) {
        user.setRoles(roleList);
        userService.updateUser(user);
        return "redirect:/admin";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
