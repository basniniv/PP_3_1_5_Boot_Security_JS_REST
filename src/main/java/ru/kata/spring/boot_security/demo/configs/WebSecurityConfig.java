package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private final UserServiceImpl useServiceImpl;
    private final PasswordEncoderConfig passwordEncoderConfig;

    public WebSecurityConfig(SuccessUserHandler successUserHandler,
                             UserServiceImpl useServiceImpl,
                             PasswordEncoderConfig passwordEncoderConfig) {
        this.successUserHandler = successUserHandler;
        this.useServiceImpl = useServiceImpl;
        this.passwordEncoderConfig = passwordEncoderConfig;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoderConfig.passwordEncoder());
        authenticationProvider.setUserDetailsService(useServiceImpl);
        return authenticationProvider;
    }

}