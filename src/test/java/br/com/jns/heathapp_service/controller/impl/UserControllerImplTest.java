package br.com.jns.heathapp_service.controller.impl;

import br.com.jns.heathapp_service.domain.UserDomain;
import br.com.jns.heathapp_service.models.request.CreateUserRequest;
import br.com.jns.heathapp_service.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.jns.heathapp_service.creator.CreatorUtils.generateMock;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerImplTest {
    public static final String BASE_URI = "/health-app/v1/users";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @Test
    void testFindByIdWithSuccess() throws Exception {
        final var entity = generateMock(UserDomain.class).withId(null);

        final var user = repository.save(entity);

        mockMvc.perform(get("/health-app/v1/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.password").value(entity.getPassword()))
                .andExpect(jsonPath("$.profile").isArray());

        repository.deleteById(user.getId());
    }

    @Test
    void testFindByIdWithNotFoundException() throws Exception {
        mockMvc.perform(get("/health-app/v1/users/{id}", "123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Object not found Id: 123, Type: UserResponse"))
                .andExpect(jsonPath("$.path").value("/health-app/v1/users/123"))
                .andExpect(jsonPath("$.error").value(NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

    }

    @Test
    void findAllWithSuccess() throws Exception {
        final var entity1 = generateMock(UserDomain.class).withId(null);
        final var entity2 = generateMock(UserDomain.class).withId(null);

        final var users = repository.saveAll(List.of(entity1, entity2));
        repository.findAll();

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[1]").isNotEmpty())
                .andExpect(jsonPath("$[0].profile").isArray())
                .andExpect(jsonPath("$[1].profile").isArray());

        repository.deleteAll(List.of(entity1, entity2));

    }

    @Test
    @Transactional
    void saveUsersWithSuccess() throws Exception {
        final var emailValid = "testitegrationemail@test.com";
        final var request = generateMock(CreateUserRequest.class).withEmail(emailValid);

        mockMvc.perform(
                post(BASE_URI)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(request))

        ).andExpect(status().isCreated());

        repository.deleteByEmail(emailValid);
    }

    @Test
    @Transactional
    void saveUsersWithConflict() throws Exception {
        final var emailValid = "testitegrationemail@test.com";
        final var entity = generateMock(UserDomain.class)
                .withEmail(emailValid)
                .withId(null);

        repository.save(entity);


        var request = generateMock(CreateUserRequest.class).withEmail(emailValid);

        mockMvc.perform(
                        post(BASE_URI)
                                .contentType(APPLICATION_JSON)
                                .content(toJson(request))

                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email: [" + emailValid + "] already exists"))
                .andExpect(jsonPath("$.error").value(CONFLICT.getReasonPhrase()))
                .andExpect(jsonPath("$.status").value(CONFLICT.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
        repository.deleteById(entity.getId());
    }

    @Test
    @Transactional
    void saveUsersWithEmptyNameAndThrowBadRequest() throws Exception {
        final var emailValid = "testitegrationemail@test.com";

        var request = generateMock(CreateUserRequest.class)
                .withEmail(emailValid).withName("");

        mockMvc.perform(
                        post(BASE_URI)
                                .contentType(APPLICATION_JSON)
                                .content(toJson(request))

                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Exception in validation attributes"))
                .andExpect(jsonPath("$.error").value("Validation Exception"))
                .andExpect(jsonPath("$.status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.errors[?(@.fieldName=='name' && @.message=='Name cannot be empty')]").exists())
                .andExpect(jsonPath("$.errors[?(@.fieldName=='name' && @.message=='Name must contain between 3 and 50 characters')]").exists());
    }


    private String toJson(Object object) throws Exception {

        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception ex) {
            throw new Exception("Erro to convert object to json");
        }

    }


}