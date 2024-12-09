package AviaBooking.security;

import AviaBooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;


import java.util.Collection;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer {

    private final UserService userService;

    @Autowired
    public WebSecurityConfigurer(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Отключение CSRF
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login-form", "/users/create").permitAll()
//                        .requestMatchers("/managers/**").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers("/phones/**", "/").hasAnyAuthority("ADMIN", "USER")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login-form")
//                        .defaultSuccessUrl("/managers/all")
//                        .failureUrl("/login-form?error=true")
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/login-form?logout=true")
//                        .deleteCookies("JSESSIONID")
//                        .clearAuthentication(true)
//                        .permitAll()
//                );

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login-form", "/users/create").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/**").hasAuthority("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login-form")
                        .successHandler((request, response, authentication) -> {
                            String username = authentication.getName(); // Получаем имя пользователя (email или логин)
                            int userId = userService.readByEmail(username).getId();; // Ищем ID пользователя по логину
                            response.sendRedirect("users/profile/" + userId); // Перенаправляем на персональную страницу
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login-form?logout=true")
                        .permitAll()
                );

        return http.build();
    }

    private String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                return "/admin/home";
            } else if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                return "/user/home";
            }
        }
        throw new IllegalStateException("Unexpected role");
    }
}
