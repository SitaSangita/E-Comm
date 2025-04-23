package sita.sangita.customerAPI.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.SneakyThrows;
import sita.sangita.customerAPI.service.CustomerServiceImpl;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final CustomerServiceImpl customerserviceImp;

    public AppSecurityConfig(@Lazy CustomerServiceImpl customerserviceImp) {
        this.customerserviceImp = customerserviceImp;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider();
        authprovider.setUserDetailsService(customerserviceImp);
        authprovider.setPasswordEncoder(passwordEncoder());
        return authprovider;
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain security(HttpSecurity http) {
        http.authorizeHttpRequests(req -> req
                .requestMatchers("/register", "/login", "/resetPwd", "/forgot-pwd/**")
                .permitAll()
                .anyRequest()
                .authenticated());
        return http.csrf().disable().build();
    }
}
