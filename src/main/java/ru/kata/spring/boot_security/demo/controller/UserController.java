package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;



@Controller
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("user")
    public String getUserInfo(Model model,Principal principal) {
        Optional<User> currentUserOpt = userService.findByUsername(principal.getName());

        if (currentUserOpt.isPresent()) {
            User currentUser = currentUserOpt.get();
            model.addAttribute("currentUser", currentUser);
        } else {
            // Обработка случая, когда пользователь не найден
            model.addAttribute("currentUser", null);
        }
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "user";

    }


}