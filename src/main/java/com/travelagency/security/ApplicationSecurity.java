package com.travelagency.security;

import com.travelagency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ApplicationSecurity {
     private final UserRepository userRepo;
     private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepo.findByEmail(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("User " + username + " not found"));
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));


        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http
            .authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/api/cities/**", "/api/countries/**", "/api/users/**", "/api/hotels/**", "/api/trips/**", "/api/variations/**", "/api/auth/**")
            .permitAll()
            .requestMatchers(HttpMethod.POST, "/api/auth/**") //    /api/auth/register and /api/auth/authenticate
            .permitAll()

            .requestMatchers(HttpMethod.POST, "/api/users/**", "/api/trips/**")
            .hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/users/**", "/api/trips/**")
            .hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/users/**", "/api/trips/**")
            .hasAnyRole("USER", "ADMIN")

            .requestMatchers(HttpMethod.POST, "/api/cities/**", "/api/countries/**", "/api/hotels/**", "/api/variations/**")
            .hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/cities/**", "/api/countries/**", "/api/hotels/**", "/api/variations/**")
            .hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/cities/**", "/api/countries/**", "/api/hotels/**", "/api/variations/**")
            .hasRole("ADMIN").anyRequest().authenticated()

            .and()

            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}
