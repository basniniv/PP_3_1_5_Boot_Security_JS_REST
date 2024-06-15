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
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/", "/index","/exit" ).permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().ignoringAntMatchers("/api/**")
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/exit")
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