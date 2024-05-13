package com.kata.pre_3_1_2_sb.controller;

import com.kata.pre_3_1_2_sb.domain.UserDb;
import com.kata.pre_3_1_2_sb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/new")
    public String createNewUser(@ModelAttribute("user") UserDb user) {
        return "new";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") UserDb user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable UUID id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") UserDb user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/user")
    public String getUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userService.getUser(userDetails));
        return "user";
    }

    @GetMapping("/admin")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "admin";
    }
}
