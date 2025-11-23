package org.npeonelove.backend.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for user authentication credentials")
public class UserCredentialsRequestDTO {

    @NotBlank(message = "Enter a email")
    @Email(message = "Enter a correct email")
    @Size(min = 5, max = 128, message = "The length of the email should be from 5 to 128 characters")
    @Schema(
            description = "User's email address",
            example = "user@example.com",
            minLength = 5,
            maxLength = 128,
            format = "email",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @NotBlank(message = "Enter a password")
    @Size(min = 6, max = 32, message = "The length of the password should be from 6 to 32 characters")
    @Schema(
            description = "User's password",
            example = "SecurePass123!",
            minLength = 6,
            maxLength = 32,
            format = "password",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String password;
}