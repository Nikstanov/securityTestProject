package com.nikstanov.securityTestProject.config;

import com.nikstanov.securityTestProject.security.AuthProviderImpl;
import com.nikstanov.securityTestProject.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.PasswordManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;
    private final AuthProviderImpl authProvider;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, AuthProviderImpl authProvider) {
        this.personDetailsService = personDetailsService;
        this.authProvider = authProvider;
    }

//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(personDetailsService);
//    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Configure AuthenticationManagerBuilder
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(personDetailsService)
//                .passwordEncoder(getPasswordEncoder());
//        // Get AuthenticationManager
//        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/auth/login", "/error", "/auth/registration", "/auth/permitPage").permitAll()
                        .anyRequest().hasAnyRole("USER", "ADMIN")
                )
                .userDetailsService(personDetailsService)
                .exceptionHandling().accessDeniedPage("/error")
//                .and().formLogin()
//                    .loginPage("/auth/login")
//                    .loginProcessingUrl("/process_login")
//                .defaultSuccessUrl("/main",true)
//                .failureUrl("/auth/login?error")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");
        return http.build();
    }
}
