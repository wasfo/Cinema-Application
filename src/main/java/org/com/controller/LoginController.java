package org.com.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {


    @GetMapping("/login")
    public String loginForm() {
        return "fancyLogin";
    }

    @GetMapping("/successLogin")
    public String successLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin";
        }
        return "redirect:/cinemas";
    }
}
