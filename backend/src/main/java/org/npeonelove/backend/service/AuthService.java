package org.npeonelove.backend.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.modelmapper.ModelMapper;
import org.npeonelove.backend.dto.jwt.JwtAuthenticationDTO;
import org.npeonelove.backend.dto.jwt.RefreshTokenDTO;
import org.npeonelove.backend.dto.user.ChangePasswordRequestDTO;
import org.npeonelove.backend.dto.user.ChangePasswordResponseDTO;
import org.npeonelove.backend.dto.user.UserCredentialsRequestDTO;
import org.npeonelove.backend.exception.auth.EmailNotUniqueException;
import org.npeonelove.backend.exception.auth.PasswordChangeException;
import org.npeonelove.backend.exception.auth.UserNotFoundException;
import org.npeonelove.backend.model.user.User;
import org.npeonelove.backend.model.user.UserRoleEnum;
import org.npeonelove.backend.repository.UserRepository;
import org.npeonelove.backend.security.SecurityService;
import org.npeonelove.backend.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final SecurityService securityService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
//    private final RedisTemplate<String, String> redisTemplate;

    @Value("${refresh-token.ttl}")
    private int refreshTokenTtl;

    // регистрация пользователя
    @Transactional
    public JwtAuthenticationDTO signUp(UserCredentialsRequestDTO userCredentialsRequestDTO) {

        if (!isEmailUnique(userCredentialsRequestDTO.getEmail())) {
            throw new EmailNotUniqueException("Email already exists");
        }

        User user = new User();

        user.setUserId(UUID.randomUUID());
        user.setEmail(userCredentialsRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userCredentialsRequestDTO.getPassword()));

        user.setRole(UserRoleEnum.USER);
        user.setRegistrationDate(LocalDateTime.now());

        userRepository.save(user);

        return jwtService.generateAuthToken(user.getUserId());
    }

    // авторизация пользователя
    @Transactional
    public JwtAuthenticationDTO signIn(UserCredentialsRequestDTO userCredentialsRequestDTO) throws AuthenticationException {

        User user = userRepository.findUserByEmail(userCredentialsRequestDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User with email " + userCredentialsRequestDTO.getEmail() + " not found"));

        if (!passwordEncoder.matches(userCredentialsRequestDTO.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }

        return jwtService.generateAuthToken(user.getUserId());
    }

//    // выход из аккаунта пользователя
//    @Transactional
//    public Boolean signOut(RefreshTokenDTO refreshTokenDTO) {
//
//        if (redisTemplate.hasKey(refreshTokenDTO.getRefreshToken())) {
//            throw new SignOutException("Refresh token already expired");
//        }
//
//        redisTemplate.opsForValue().set(refreshTokenDTO.getRefreshToken(), "1", refreshTokenTtl);
//        return Boolean.TRUE;
//    }

    // обновление пароля пользователя
    @Transactional
    public ChangePasswordResponseDTO changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {

        User user = userRepository.findUserByUserId(securityService.getUUIDFromSecurityContext())
                .orElseThrow(() ->  new UserNotFoundException("User with id " + securityService.getUUIDFromSecurityContext() + " not found"));

        String userPassword = user.getPassword();

        if (!passwordEncoder.matches(changePasswordRequestDTO.getOldPassword(),  userPassword)) {
            throw new PasswordChangeException("Old password is incorrect");
        }

        if (passwordEncoder.matches(changePasswordRequestDTO.getNewPassword(), userPassword)) {
            throw new PasswordChangeException("New password must differ from the old one");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequestDTO.getNewPassword()));

        return modelMapper.map(userRepository.save(user), ChangePasswordResponseDTO.class);

    }

    // генерация access токена по refresh токену
    public JwtAuthenticationDTO refreshAccessToken(RefreshTokenDTO refreshTokenDTO) throws AuthenticationException {

//        if (redisTemplate.hasKey(refreshTokenDTO.getRefreshToken())) {
//            throw new AuthenticationException("Refresh token is already expired");
//        }

        String refreshToken = refreshTokenDTO.getRefreshToken();

        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = userService.getUserByUUID(UUID.fromString(jwtService.getUserIdFromJwtToken(refreshToken)));
            return jwtService.refreshAccessToken(user.getUserId(), refreshToken);
        }

        throw new AuthenticationException("Invalid refresh token");
    }

    // проверка уникальности email
    private Boolean isEmailUnique(String email) {
        return !userRepository.existsUserByEmail(email);
    }

}
