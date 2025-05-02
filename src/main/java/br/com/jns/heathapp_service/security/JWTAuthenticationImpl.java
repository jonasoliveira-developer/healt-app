package br.com.jns.heathapp_service.security;

import br.com.jns.heathapp_service.models.request.AuthenticateRequest;
import br.com.jns.heathapp_service.models.response.AuthenticateResponse;
import br.com.jns.heathapp_service.security.dtos.UserDetailsDTO;
import br.com.jns.heathapp_service.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthenticationImpl {
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    public AuthenticateResponse authenticate(final AuthenticateRequest request) {
        try {
            log.info("Authenticating user: {}", request.email());
            final var authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            return buildAuthenticationResponse((UserDetailsDTO) authResult.getPrincipal());

        }catch (BadCredentialsException ex) {
            log.error("Error authenticate User: {}", request.password());
            throw new BadCredentialsException("Email or Password invalid");
        }
    }

    protected AuthenticateResponse buildAuthenticationResponse(final UserDetailsDTO detailsDTO) {
        log.info("User authenticated with success: {}", detailsDTO.getUsername());
        final var token = jwtUtils.generateToken(detailsDTO);
        return AuthenticateResponse.builder()
                .type("Bearer")
                .token(token)
                .build();
    }
}
