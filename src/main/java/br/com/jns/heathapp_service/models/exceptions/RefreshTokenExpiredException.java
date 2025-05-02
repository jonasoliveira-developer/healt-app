package br.com.jns.heathapp_service.models.exceptions;

public class RefreshTokenExpiredException extends RuntimeException {
     public RefreshTokenExpiredException(String message) {
        super(message);
    }
}
