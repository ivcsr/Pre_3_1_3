package com.kata.pre_3_1_2_sb.controller;

import com.kata.pre_3_1_2_sb.model.User;
import com.kata.pre_3_1_2_sb.service.RoleService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("newUser") User user, @RequestParam("listRoles") List<Integer> roleList) {
        user.setRoles(roleService.findRoles(roleList));
        userService.save(user);
        return "redirect:/admin";
    }
    @GetMapping()
    public String getAdmin(@AuthenticationPrincipal UserDetails userDetails, Model model,
                           @ModelAttribute("newUser") User user) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("currentUser", userService.getUser(userDetails));
        model.addAttribute("roleList", roleService.getAllRoles());
        return "admin";
    }

    @PatchMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("listRoles") List<Integer> roleList) {
        user.setRoles(roleService.findRoles(roleList));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
