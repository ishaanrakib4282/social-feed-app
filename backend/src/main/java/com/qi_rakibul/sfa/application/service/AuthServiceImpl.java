package com.qi_rakibul.sfa.application.service;

import com.qi_rakibul.sfa.api.payload.request.LoginRequest;
import com.qi_rakibul.sfa.api.payload.request.SignupRequest;
import com.qi_rakibul.sfa.api.payload.response.AuthResponse;
import com.qi_rakibul.sfa.application.dao.UserDao;
import com.qi_rakibul.sfa.application.domain.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    @Override
    public void signup(SignupRequest request) {

        if (userDao.existsByEmail(request.email())) {
            throw new IllegalArgumentException(
                    "Email already exists"
            );
        }

        UserEntity userEntity = UserEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .passwordHash(
                        passwordEncoder.encode(
                                request.password()
                        )
                )
                .build();

        userDao.save(userEntity);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        UserEntity user = userDao
                .findByEmail(request.email())
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Invalid email or password"
                        )
                );

        boolean passwordMatches = passwordEncoder.matches(
                request.password(), user.getPasswordHash()
        );

        if (!passwordMatches) {
            throw new IllegalArgumentException(
                    "Invalid email or password"
            );
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}