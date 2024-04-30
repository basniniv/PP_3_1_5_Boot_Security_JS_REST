package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserDetService;


@Controller
public class UserController {
   UserDetService userDetService;
    private final RoleRepository roleRepository;
    @Autowired
    public UserController(UserDetService userDetService, RoleRepository roleRepository) {
        this.userDetService = userDetService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("user")
    public String getUserInfo(Model model,Principal principal) {
        User currentUser = userDetService.findByUsername(principal.getName());
        model.addAttribute("currentUser", currentUser);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "user";

    }


}