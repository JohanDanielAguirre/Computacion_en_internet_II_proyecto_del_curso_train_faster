package org.example.trainfaster.services.implementations;

import lombok.RequiredArgsConstructor;
import org.example.trainfaster.DTO.AuthResponse;
import org.example.trainfaster.configs.JwtService;
import org.example.trainfaster.DTO.LoginRequest;
import org.example.trainfaster.DTO.RegisterRequest;
import org.example.trainfaster.model.Role;
import org.example.trainfaster.model.Users;
import org.example.trainfaster.repositories.RoleRepository;
import org.example.trainfaster.services.interfaces.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthResponse login(LoginRequest request) {
        System.out.println(request.getUsername());
        System.out.println(request.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            System.out.println("Error en autenticación: " + e.getMessage());
            throw e;
        }

        // Obtener detalles del usuario si la autenticación es exitosa
        UserDetails user = (UserDetails) userService.findUserByName(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        //UserDetails user = userService.loadUserByUsername(request.getUsername());

        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        List<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toList());

        Users user = Users.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .name(request.getName())
                .lastName(request.getLastName())
                .roles(roles)
                .build();

        userService.saveUser(user);

        return AuthResponse.builder()
                .token(jwtService.getToken((UserDetails) user))
                .build();
    }

}
