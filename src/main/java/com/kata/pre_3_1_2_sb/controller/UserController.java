package com.kata.pre_3_1_2_sb.controller;

import com.kata.pre_3_1_2_sb.service.RoleService;
import com.kata.pre_3_1_2_sb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    @GetMapping()
    public String getUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("currentUser", userService.getUser(userDetails));
        return "user";
    }
}
