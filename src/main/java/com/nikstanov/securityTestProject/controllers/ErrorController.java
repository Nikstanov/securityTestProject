package com.nikstanov.securityTestProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    public String errorPage(){
        System.out.println("Some error");
        return "error";
    }
}
