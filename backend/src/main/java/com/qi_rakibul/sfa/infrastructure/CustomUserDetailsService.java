package com.qi_rakibul.sfa.infrastructure;

import com.qi_rakibul.sfa.application.dao.UserDao;
import com.qi_rakibul.sfa.application.domain.AuthenticatedUser;
import com.qi_rakibul.sfa.application.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = userDao.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        return new AuthenticatedUser(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash()
        );
    }
}