package com.nikstanov.securityTestProject.controllers;

import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("some", (session.getAttribute("some") != null) ? (int)session.getAttribute("some") + 1 : 1);
        System.out.println(session.getAttribute("some"));

        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            System.out.println("cookie" + cookie.getName() + ": " + cookie.getValue());
        }
        Cookie cookie = new Cookie("someCookie", "addSomeCookie");
        cookie.setMaxAge(24 * 60);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "main";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminPage(){
        return "/admin";
    }
}
