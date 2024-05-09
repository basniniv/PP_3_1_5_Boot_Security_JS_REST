package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final RoleService roleService;


    public AdminController(UserService userService, RoleRepository roleRepository, RoleService roleService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

    //*****************************************//show all users//*******************************************
    @GetMapping("/admin")
    public String getUsers(Model model) {
        List<User> usersList = userService.getUsers();
        model.addAttribute("users", usersList);
        return "admin/users";
    }
    //**************************************//create//************************************************

    @GetMapping("/addUser")
    public String addUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "admin/addUser";
    }

    @PostMapping("/addUserToDB")
    public String addUser(@ModelAttribute("user") @Valid User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    //***************************************//remove//********************************************
    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }

    //*****************************************//edit//********************************************

    @GetMapping("/edit/{id}")
    public String updateUserForm(Model model, @PathVariable("id") Integer id) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAll());
        return "admin/update";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/update";
        }
        userService.update(user);
        return "redirect:/admin";
    }
}
