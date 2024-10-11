package com.devandre.moviebox.shared.infrastructure.security.service;

import com.devandre.moviebox.shared.infrastructure.security.model.UserDetailsSecurityImpl;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.JpaUserRepository;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JpaUserRepository userRepository;

    public UserDetailsServiceImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));
        return UserDetailsSecurityImpl.build(user);
    }
}
