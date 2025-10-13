package com.ecodoobiz.hlbudi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.ecodoobiz.hlbudi.repository.ManagerRepository;

@Configuration
public class SecurityConfig {

    private final AdminProperties adminProperties;
    private final ManagerRepository managerRepository;

    public SecurityConfig(AdminProperties adminProperties, ManagerRepository managerRepository) {
        this.adminProperties = adminProperties;
        this.managerRepository = managerRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/manager/**").hasRole("MANAGER")
                                .requestMatchers("/").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .successHandler(authenticationSuccessHandler())
                );
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/admin/dashboard");
            } else {
                response.sendRedirect("/");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return username -> {
            // 1. Check for admin user from admin.yml
            if (adminProperties.getUsername().equals(username)) {
                return User.builder()
                        .username(adminProperties.getUsername())
                        .password(passwordEncoder.encode(adminProperties.getPassword()))
                        .roles(adminProperties.getRoles())
                        .build();
            }

            // 2. Fall back to database for other users
            return managerRepository.findByUsername(username)
                    .map(manager -> User.builder()
                            .username(manager.getUsername())
                            .password(manager.getPassword())
                            .roles("MANAGER") // Assuming all users from Manager table are MANAGERs
                            .build())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        };
    }
}