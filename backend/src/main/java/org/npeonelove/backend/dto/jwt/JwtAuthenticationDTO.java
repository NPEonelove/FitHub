package org.npeonelove.backend.dto.jwt;

import lombok.Data;

import java.util.UUID;

@Data
public class JwtAuthenticationDTO {

    private String accessToken;
    private String refreshToken;

    // временно, пока нет логики создания профиля
    private UUID userId;

}
