package br.com.jns.heathapp_service.controller;

import br.com.jns.heathapp_service.models.exceptions.StandardError;
import br.com.jns.heathapp_service.models.request.AuthenticateRequest;
import br.com.jns.heathapp_service.models.request.RefreshTokenRequest;
import br.com.jns.heathapp_service.models.response.AuthenticateResponse;
import br.com.jns.heathapp_service.models.response.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/health-app/api/auth")
public interface AuthController {

    @Operation(summary = "Authenticate User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthenticateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping("/login")
    ResponseEntity<AuthenticateResponse> authenticate(@Valid @RequestBody final AuthenticateRequest request) throws Exception;

    @Operation(summary = "Refresh Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthenticateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping("refresh-token")
    ResponseEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody final RefreshTokenRequest request);
}
