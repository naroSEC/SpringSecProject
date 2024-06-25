package com.spbt.secproject.controller.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {

    @GetMapping(value = "/admin")
    public String adminPage() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        System.out.println("==============================");
        System.out.println(authentication.getAuthorities());
        System.out.println("==============================");

        return "/admin/index";
    }

}
