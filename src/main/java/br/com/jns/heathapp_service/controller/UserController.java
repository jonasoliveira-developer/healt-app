package br.com.jns.heathapp_service.controller;

import br.com.jns.heathapp_service.domain.UserDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/health-app/v1/users")
public interface UserController {

    @GetMapping("/{id}")
    ResponseEntity<UserDomain> findById(@PathVariable final String id);
}
