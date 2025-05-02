package br.com.jns.heathapp_service.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticateRequest(
        @Schema(description = "User e-mail", example = "jon_do@email.com")
        @Email(message = "Email invalid")
        @NotBlank(message = "Email cannot be empty" )
        @Size(min = 8, max = 50, message = "Email must contain between 8 and 50 characters")
        String email,

        @Schema(description = "User password", example = "123456")
        @NotBlank(message = "Password cannot be empty")
        @Size(min = 8, max = 50, message = "Password must contain between 8 and 50 characters")
        String password
) {
}
