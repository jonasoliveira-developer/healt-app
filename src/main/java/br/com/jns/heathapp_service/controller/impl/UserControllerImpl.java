package br.com.jns.heathapp_service.controller.impl;

import br.com.jns.heathapp_service.controller.UserController;
import br.com.jns.heathapp_service.models.request.CreateUserRequest;
import br.com.jns.heathapp_service.models.request.UpdateUserRequest;
import br.com.jns.heathapp_service.models.response.UserResponse;
import br.com.jns.heathapp_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController {

    final UserService service;

    @Override
    public ResponseEntity<UserResponse> findById(String id) {

        log.info("REC Id : {}", id);
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Override
    public ResponseEntity<Void> save(CreateUserRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED.value()).build();
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @Override
    public ResponseEntity<UserResponse> update(UpdateUserRequest request, String id) {
        return ResponseEntity.ok().body(service.update(request, id));
    }
}
