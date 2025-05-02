package br.com.jns.heathapp_service.utils;

import br.com.jns.heathapp_service.security.dtos.UserDetailsDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(final UserDetailsDTO detailsDTO) {

        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

        return Jwts.builder()
                .claim("id", detailsDTO.getId())
                .claim("name", detailsDTO.getName())
                .claim("authorities", detailsDTO.getAuthorities())
                .claim("sub", detailsDTO.getUsername())
                .signWith(key)
                .claim("exp", new Date(System.currentTimeMillis() + expiration))
                .compact();

    }
}
