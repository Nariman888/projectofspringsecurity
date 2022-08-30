package kz.project.springsecurity.projectofspringsecurity.config;

import kz.project.springsecurity.projectofspringsecurity.services.UserService;
import kz.project.springsecurity.projectofspringsecurity.services.imp.UserServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(proxyTargetClass = true, prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Bean
    public UserService userService(){
        return new UserServiceImp();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userService()).passwordEncoder(passwordEncoder());

        http.exceptionHandling().accessDeniedPage("/forbidden");
        http.authorizeRequests().antMatchers("/css/**", "/js/**").permitAll();

        http.formLogin()
                .loginProcessingUrl("/auth").permitAll()//<form action="/auth" method="post">
                .defaultSuccessUrl("/profile")
                .failureUrl("/signin?error").permitAll()
                .usernameParameter("user_email")
                .passwordParameter("user_password")
                .loginPage("/signin").permitAll();

        http.logout()
                .logoutSuccessUrl("/signin")
                .logoutUrl("/signout");

        http.csrf().disable();

        return http.build();
    }
}
