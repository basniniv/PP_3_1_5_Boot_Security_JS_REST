package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;



    @GetMapping()
    public String allUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("user", new User());
        List<Role> allRoles = roleService.getRolesList();
        model.addAttribute("allRoles", allRoles);
        return "admin/users";
    }

    @GetMapping("/{id}")
    public String user(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin/user";
    }


    @GetMapping("/{id}/edit")
    public String showEditUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        List<Role> allRoles = roleService.getRolesList();
        model.addAttribute("allRoles", allRoles);
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User editedUser) {
        userService.saveUser(editedUser);
        return "redirect:/admin/";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, Model model) {
        List<Role> allRoles = roleService.getRolesList();
        model.addAttribute("allRoles", allRoles);
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }




}


