package ru.kata.spring.boot_security.demo.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    //*****************************************//users//*******************************************
    @GetMapping("/admin")
    public String getUsers(Model model) {
        List<User> usersList = userDetService.allUsers();
        model.addAttribute("users", usersList);
        return "admin/users";
    }
    //**************************************//add//************************************************

    @GetMapping("/addUser")
    public String addUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "admin/addUser";
    }
    @PostMapping("/addUserToDB")
    public String addUser(@ModelAttribute("user") @Valid User user,Model model) {
        userDetService.saveUser(user);
        return "redirect:/login";
    }
    //***************************************//remove//********************************************
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") long id) {
        userDetService.deleteUser(id);
        return "redirect:/admin/users";

    }
    //*****************************************//edit//********************************************
    @GetMapping("/edit")
    public String updateUserForm(Model model,@RequestParam("id") long id) {
        model.addAttribute("user", userDetService.getUser(id));
        return "admin/update";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user){
        userDetService.updateUser(user);
        return "redirect:/admin/users";
    }
}
