package org.npeonelove.backend.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Response DTO containing user information after successful password change")
public class ChangePasswordResponseDTO {

    @Schema(
            description = "Unique identifier of the user",
            example = "123e4567-e89b-12d3-a456-426614174000",
            format = "uuid"
    )
    private UUID userId;

    @Schema(
            description = "User's email address",
            example = "user@example.com",
            format = "email"
    )
    private String email;

    @Schema(
            description = "Original date and time when user registered",
            example = "2023-01-15T09:30:00",
            format = "date-time"
    )
    private LocalDateTime registrationDate;
}