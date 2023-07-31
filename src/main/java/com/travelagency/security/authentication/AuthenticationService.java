package com.travelagency.security.authentication;

import com.travelagency.dto.User;
import com.travelagency.repository.UserRepository;
import com.travelagency.security.authentication.AuthRequest;
import com.travelagency.security.authentication.AuthResponse;
import com.travelagency.security.authentication.RegisterRequest;
import com.travelagency.security.Role;
import com.travelagency.security.config.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthResponse authenticateRequest(AuthRequest request){

            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            if(user == null){
                throw new BadCredentialsException("credentials are not correct");
            }
            String accessToken = jwtUtil.generateAccessToken(user);
        return new AuthResponse(user.getEmail(), accessToken);
    }

    public AuthResponse registerUser(RegisterRequest request){
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        System.out.println("it' here");
        User savedUser = userRepository.save(user);
        System.out.println("it' here now");

        var jwtToken = jwtUtil.generateAccessToken(user);
        return new AuthResponse(user.getEmail(),jwtToken);


    }


}
