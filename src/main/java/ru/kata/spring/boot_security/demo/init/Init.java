//package ru.kata.spring.boot_security.demo.init;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.UserDetService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class Init implements ApplicationListener<ContextRefreshedEvent> {
//
//
//    private final UserDetService userService;
//
//
//
//    @Autowired
//    public Init(UserDetService userService, UserDetailsService userDetailsService) {
//        this.userService = userService;
//
//    }
//
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        Role userRole = new Role();
//        userRole.setRolename("ROLE_USER");
//
//
//        Role adminRole = new Role();
//        adminRole.setRolename("ROLE_ADMIN");
//
//
//        List<Role> userRoles = new ArrayList<>(List.of(userRole));
//        List<Role> adminRoles = new ArrayList<>(List.of(adminRole));
//
//        User admin = new User();
//        admin.setUsername("admin");
//        admin.setLastName("Basnin");
//        admin.setAge(30);
//        admin.setEmail("admin@mail.ru");
//        admin.setPassword("admin");
//        admin.setRoles(adminRoles);
//        userService.saveUser(admin);
//
//        User user = new User();
//        user.setUsername("user");
//        user.setLastName("user2");
//        user.setAge(18);
//        user.setEmail("user@mail.ru");
//        user.setPassword("user");
//        user.setRoles(userRoles);
//        userService.saveUser(user);
//    }
//}
