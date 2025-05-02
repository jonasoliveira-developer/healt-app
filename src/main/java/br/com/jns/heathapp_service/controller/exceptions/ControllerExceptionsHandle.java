package br.com.jns.heathapp_service.controller.exceptions;

import br.com.jns.heathapp_service.models.exceptions.ObjectNotFoundException;
import br.com.jns.heathapp_service.models.exceptions.RefreshTokenExpiredException;
import br.com.jns.heathapp_service.models.exceptions.StandardError;
import br.com.jns.heathapp_service.models.exceptions.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ControllerExceptionsHandle {

    @ExceptionHandler({BadCredentialsException.class, RefreshTokenExpiredException.class})
    ResponseEntity<StandardError> handleBadCredentialsException(final RuntimeException ex, HttpServletRequest request) {
       return ResponseEntity.status(UNAUTHORIZED).body(
                StandardError.builder()
                        .timestamp(now())
                        .error(UNAUTHORIZED.getReasonPhrase())
                        .status(UNAUTHORIZED.value())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }


    @ExceptionHandler(ObjectNotFoundException.class)
    ResponseEntity<StandardError> handleNotFoundException(
            final ObjectNotFoundException ex,
            final HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                StandardError.builder()
                            .timestamp(now())
                            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                            .status(HttpStatus.NOT_FOUND.value())
                            .message(ex.getMessage())
                            .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<StandardError> usernameNotFoundException(
            final UsernameNotFoundException ex,
            final HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                StandardError.builder()
                        .timestamp(now())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<StandardError> dataIntegrityViolationException(
            final DataIntegrityViolationException ex,
            final HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                StandardError.builder()
                        .timestamp(now())
                        .error(HttpStatus.CONFLICT.getReasonPhrase())
                        .status(HttpStatus.CONFLICT.value())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationException> methodArgumentNotValidException(
            final MethodArgumentNotValidException ex,
            final HttpServletRequest request
    ) {
        var error = ValidationException
                .builder()
                        .timestamp(now())
                        .error("Validation Exception")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Exception in validation attributes")
                        .path(request.getRequestURI())
                        .errors(new ArrayList<>())
                .build();

       for(FieldError fieldError: ex.getBindingResult().getFieldErrors()) {
            error.addError(fieldError.getField(), fieldError.getDefaultMessage());
       }


        return ResponseEntity.badRequest().body(error);

    }
}
