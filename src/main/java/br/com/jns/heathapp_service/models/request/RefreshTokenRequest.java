package br.com.jns.heathapp_service.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest (
        @Size(min = 36, max = 40, message = "Refresh token must be between 16 and 30 characters ")
                @NotBlank(message = "Refresh  token is required")
        String refreshToken
){
}
