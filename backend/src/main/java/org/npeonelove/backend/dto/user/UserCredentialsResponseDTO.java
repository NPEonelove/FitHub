package org.npeonelove.backend.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "DTO containing authenticated user information with credentials")
public class UserCredentialsResponseDTO {

    @Schema(
            description = "Unique identifier of the authenticated user",
            example = "123e4567-e89b-12d3-a456-426614174000",
            format = "uuid"
    )
    private UUID userId;

    @Schema(
            description = "User's verified email address",
            example = "user@example.com",
            format = "email"
    )
    private String email;

    @Schema(
            description = "Timestamp of user registration",
            example = "2023-01-15T09:30:00",
            format = "date-time"
    )
    private LocalDateTime registrationDate;
}