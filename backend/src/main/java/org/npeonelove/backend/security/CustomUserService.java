package org.npeonelove.backend.security;

import lombok.RequiredArgsConstructor;
import org.npeonelove.backend.exception.auth.UserNotFoundException;
import org.npeonelove.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findUserByUserId(UUID.fromString(userId)).map(CustomUserDetails::new).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId + " not found"));
    }
}
