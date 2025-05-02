package br.com.jns.heathapp_service.controller.impl;

import br.com.jns.heathapp_service.controller.AuthController;
import br.com.jns.heathapp_service.models.request.AuthenticateRequest;
import br.com.jns.heathapp_service.models.request.RefreshTokenRequest;
import br.com.jns.heathapp_service.models.response.AuthenticateResponse;
import br.com.jns.heathapp_service.models.response.RefreshTokenResponse;
import br.com.jns.heathapp_service.security.JWTAuthenticationImpl;
import br.com.jns.heathapp_service.service.RefreshTokenService;
import br.com.jns.heathapp_service.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(final AuthenticateRequest request) throws Exception {
        return ResponseEntity.ok().body(
                new JWTAuthenticationImpl(authenticationConfiguration.getAuthenticationManager(), jwtUtils)
                        .authenticate(request)
                        .withRefreshToken(refreshTokenService.save(request.email()).getId())

        );
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest request) {
        return ResponseEntity.ok().body(refreshTokenService.refreshToken(request.refreshToken()));
    }
}
