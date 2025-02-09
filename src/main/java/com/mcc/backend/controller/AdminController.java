package com.mcc.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@RestController
public class AdminController {

    @PostMapping("/admin/auth/login")
    public String adminLogin(){
        return "hello admin";
    }

    @GetMapping("/admin/auth/logout")
    public String adminLogout(){
        return "Logout";
    }

    @PostMapping("/admin/auth/register")
    public String adminRegister(){
        return "done";
    }
}
