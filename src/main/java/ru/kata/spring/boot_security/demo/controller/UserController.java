package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetService;

import java.security.Principal;


@Controller
public class UserController {
    UserDetService userDetService;

    @Autowired
    public UserController(UserDetService userDetService) {
        this.userDetService = userDetService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("user")
    public String getUserInfo(Model model, Principal principal) {
        User user = userDetService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";

    }


}