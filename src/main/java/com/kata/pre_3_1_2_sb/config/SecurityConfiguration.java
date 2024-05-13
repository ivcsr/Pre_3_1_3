package com.kata.pre_3_1_2_sb.config;

import com.kata.pre_3_1_2_sb.service.UserService;
import com.kata.pre_3_1_2_sb.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    private final SuccessUserHandler successUserHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests((req) ->
                        req.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                                .antMatchers("/users/**", "/edit/**", "/new/**", "/admin/**").hasAnyRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(loginConfigurer -> loginConfigurer
                        .successHandler(successUserHandler)
                        .permitAll())
                .logout(LogoutConfigurer::permitAll);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserServiceImpl userServiceImpl, BCryptPasswordEncoder bCryptPasswordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userServiceImpl);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
