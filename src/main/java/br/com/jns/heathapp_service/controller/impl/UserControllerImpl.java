package br.com.jns.heathapp_service.controller.impl;

import br.com.jns.heathapp_service.controller.UserController;
import br.com.jns.heathapp_service.domain.UserDomain;
import br.com.jns.heathapp_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    final UserService service;
    @Override
    public ResponseEntity<UserDomain> findById(String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }
}
