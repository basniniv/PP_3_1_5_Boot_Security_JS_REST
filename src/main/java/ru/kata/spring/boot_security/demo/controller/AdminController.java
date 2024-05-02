package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserDetService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDetService userDetService;
    private final RoleRepository roleRepository;

    public AdminController(UserDetService userDetService, RoleRepository roleRepository) {
        this.userDetService = userDetService;
        this.roleRepository = roleRepository;
    }

    //===================/show all users/===================
    @GetMapping
    public String getUsers(Model model, Principal principal) {
        User currentUser = userDetService.findByUsername(principal.getName());
        model.addAttribute("currentUser", currentUser);

        List<User> usersList = userDetService.allUsers();
        model.addAttribute("users", usersList);

        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);

        User user = new User();
        model.addAttribute("user", user);

        if(currentUser == null){
            return "redirect:/login";
        }
        return "admin/users";
    }
    //===================/create/====================

    @PostMapping("/addUserToDB")
    public String addUser(@ModelAttribute("user") @Valid User user) {
        userDetService.saveUser(user);

        return "redirect:/admin/";
    }

    //===================//remove//====================
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userDetService.deleteUser(id);

        return "redirect:/admin/";

    }

    //*****************************************//update//********************************************
//    @PatchMapping("/edit/{id}")
//    public String updateUserForm(Model model, @PathVariable("id") Integer id) {
//        model.addAttribute("user", userDetService.getUser(id));
//        List<Role> roles = roleRepository.findAll();
//        model.addAttribute("allRoles", roles);
//        return "admin/update";
//    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()){
//            return "/{id}";
//        }
        userDetService.updateUser(user);
        return "redirect:/admin";
    }
}
