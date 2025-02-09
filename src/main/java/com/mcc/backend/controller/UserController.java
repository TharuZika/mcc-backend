package com.mcc.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@RestController
public class UserController {

    @PostMapping("/auth/login")
    public String userLogin(){
        return "hello";
    }

    @GetMapping("/auth/logout")
    public String userLogout(){
        return "bye";
    }

    @PostMapping("/auth/register")
    public String userRegister(){
        return "done";
    }
}
