package br.com.jns.heathapp_service.service;

import br.com.jns.heathapp_service.domain.RefreshTokenDomain;
import br.com.jns.heathapp_service.models.exceptions.ObjectNotFoundException;
import br.com.jns.heathapp_service.models.exceptions.RefreshTokenExpiredException;
import br.com.jns.heathapp_service.models.response.RefreshTokenResponse;
import br.com.jns.heathapp_service.repository.RefreshTokenRepository;
import br.com.jns.heathapp_service.security.dtos.UserDetailsDTO;
import br.com.jns.heathapp_service.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    @Value("${jwt.expiration-sec.refresh-token}")
    private  Long refreshTokenExpirationSec;

    private final RefreshTokenRepository repository;
    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;

    public RefreshTokenDomain save(final String username) {
      return   repository.save(
                RefreshTokenDomain.builder()
                        .id(UUID.randomUUID().toString())
                        .createdAt(now())
                        .expiresAt(now().plusSeconds(refreshTokenExpirationSec))
                        .username(username)
                        .build()
        );
    }

    public RefreshTokenResponse refreshToken(final String refreshTokenId) {
        final var refreshToken = repository.findById(refreshTokenId)
                .orElseThrow(() -> new ObjectNotFoundException("Refresh token not foud. Id: " + refreshTokenId));

        if (refreshToken.getExpiresAt().isBefore(now())) {
            throw new RefreshTokenExpiredException("Refresh token expired. Id: " + refreshTokenId);
        }

        return new RefreshTokenResponse(
                jwtUtils.generateToken((UserDetailsDTO) userDetailsService.loadUserByUsername(refreshToken.getUsername()))
        );
    }
}
