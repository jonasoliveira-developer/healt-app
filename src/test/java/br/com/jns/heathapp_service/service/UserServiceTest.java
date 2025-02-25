package br.com.jns.heathapp_service.service;

import br.com.jns.heathapp_service.domain.UserDomain;
import br.com.jns.heathapp_service.models.exceptions.ObjectNotFoundException;
import br.com.jns.heathapp_service.models.mapper.UserMapper;
import br.com.jns.heathapp_service.models.response.UserResponse;
import br.com.jns.heathapp_service.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    void whenCallFindByIdWithValidIdThenReturnUserResponse() {

        when(repository.findById(anyString())).thenReturn(Optional.of(new UserDomain()));
        when(mapper.fromEntity(any(UserDomain.class))).thenReturn(mock(UserResponse.class));

        final var response = service.findById("test");

        assertNotNull(response);
        assertEquals(UserResponse.class, response.getClass());
        verify(repository, times(1)).findById(anyString());
        verify(mapper, times(1)).fromEntity(any(UserDomain.class));

    }

    @Test
    void whenCallFindByIdWithInvalidIdThenThrowObjectNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        try {
            service.findById("test");
        }catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Obeject not found Id: test, Type: UserResponse", ex.getMessage());
        }

        verify(repository, times(1)).findById(anyString());
        verify(mapper, times(0)).fromEntity(any(UserDomain.class));
    }

    @Test
    void whenCallFindAllThenReturnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(new UserDomain(), new UserDomain()));
        when(mapper.fromEntity(any(UserDomain.class))).thenReturn(mock(UserResponse.class));

        final var response = service.findAll();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(UserResponse.class, response.getFirst().getClass());

        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).fromEntity(any(UserDomain.class));
    }


}