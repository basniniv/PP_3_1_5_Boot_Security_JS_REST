package ru.kata.spring.boot_security.demo.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserDetService;

@Controller

public class AdminController {

    private final UserDetService userDetService;
    private final RoleRepository roleRepository;

    public AdminController(UserDetService userDetService, RoleRepository roleRepository) {
        this.userDetService = userDetService;
        this.roleRepository = roleRepository;
    }

    //*****************************************//show all users//*******************************************
    @GetMapping("/admin")
    public String getUsers(Model model) {
        List<User> usersList = userDetService.allUsers();
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
    public String addUser(@ModelAttribute("user") @Valid User user, Model model) {
        userDetService.saveUser(user);
        return "redirect:/login";
    }

    //***************************************//remove//********************************************
    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userDetService.deleteUser(id);
        return "redirect:/admin/";
    }

    //*****************************************//edit//********************************************
    @GetMapping("/edit/{id}")
    public String updateUserForm(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userDetService.getUser(id));
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "admin/update";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userDetService.updateUser(user);
        return "redirect:/admin";
    }
}
