package br.com.jns.heathapp_service.models.response;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record AuthenticateResponse (
        String token,
        String type,
        String refreshToken
) {
}
