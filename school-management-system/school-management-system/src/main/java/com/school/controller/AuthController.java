package com.school.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * AuthController - Handles authentication related requests
 */
@Controller
public class AuthController {
    
    /**
     * Show login page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    /**
     * Redirect to appropriate dashboard based on role
     */
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_TEACHER"))) {
            return "redirect:/teacher/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
            return "redirect:/student/dashboard";
        }
        return "redirect:/login";
    }
    
    /**
     * Home page
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
}
