package br.com.jns.heathapp_service.controller;

import br.com.jns.heathapp_service.models.exceptions.StandardError;
import br.com.jns.heathapp_service.models.request.CreateUserRequest;
import br.com.jns.heathapp_service.models.request.UpdateUserRequest;
import br.com.jns.heathapp_service.models.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Tag(name = "UserController", description = "Controller responsible for users")
@RequestMapping("/health-app/v1/users")
public interface UserController {

    @Operation(summary = "find user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class)
            )),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    ))
    })
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable final String id);

    @Operation(summary = "Save new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(
                    responseCode = "400", description = "Bad request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            )

    })
    @PostMapping("/create")
    ResponseEntity<Void> save(@Valid @RequestBody CreateUserRequest request);

    @Operation(summary = "List users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User List",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400", description = "Bad request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            )

    })
    @GetMapping
    ResponseEntity<List<UserResponse>> findAll();

    @Operation(summary = "Update  user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(
                    responseCode = "400", description = "Bad request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )
            )

    })
    @PutMapping("/{id}")
    ResponseEntity<UserResponse> update(
            @Valid
            @RequestBody UpdateUserRequest request,
            @PathVariable String id
    );
}
