package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String getUsers(Model model, Principal principal) {
        Optional<User> currentUser = userService.findByUsername(principal.getName());
        if (currentUser.isPresent()) {
            model.addAttribute("currentUser", currentUser.get()); // Получаем значение из Optional
        } else {
            // Обрабатываем случай, когда пользователь не найден
            return "redirect:/login";
        }

        List<User> usersList = userService.getUsers();
        model.addAttribute("users", usersList);

        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);

        User user = new User();
        model.addAttribute("user", user);

        return "admin/users";
    }

    @PostMapping("/addUserToDB")
    public String addUser(@ModelAttribute("user") @Valid User user) {
        userService.saveUser(user);

        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);

        return "redirect:/admin/";

    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "/{id}";
        }
        userService.update(user);
        return "redirect:/admin";
    }
}
