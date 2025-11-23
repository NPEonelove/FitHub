package org.npeonelove.backend.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.npeonelove.backend.dto.user.GetJwtUserClaimsResponseDTO;
import org.npeonelove.backend.exception.auth.UserNotFoundException;
import org.npeonelove.backend.model.user.User;
import org.npeonelove.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // получение айди и роли для генерации jwt токенов
    public GetJwtUserClaimsResponseDTO getJwtUserClaims(UUID userId) {
        return modelMapper.map(getUserByUUID(userId), GetJwtUserClaimsResponseDTO.class);
    }

    // получение юзера по UUID
    public User getUserByUUID(UUID userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(
                () -> new UserNotFoundException("User with id " + userId.toString() + " not found"));
    }
}
