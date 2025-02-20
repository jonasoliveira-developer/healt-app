package br.com.jns.heathapp_service.controller.exceptions;

import br.com.jns.heathapp_service.models.exceptions.ObjectNotFoundException;
import br.com.jns.heathapp_service.models.exceptions.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDateTime.now;

@ControllerAdvice
public class ControllerExceptionsHandle {

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
}
