package br.com.jns.heathapp_service.service;

import br.com.jns.heathapp_service.domain.UserDomain;
import br.com.jns.heathapp_service.models.exceptions.ObjectNotFoundException;
import br.com.jns.heathapp_service.models.mapper.UserMapper;
import br.com.jns.heathapp_service.models.request.CreateUserRequest;
import br.com.jns.heathapp_service.models.request.UpdateUserRequest;
import br.com.jns.heathapp_service.models.response.UserResponse;
import br.com.jns.heathapp_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static br.com.jns.heathapp_service.creator.CreatorUtils.generateMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    void whenCallFindByIdWithValidIdThenReturnUserResponse() {

        when(repository.findById(anyString())).thenReturn(Optional.of(new UserDomain()));
        when(mapper.fromEntity(any(UserDomain.class))).thenReturn(generateMock(UserResponse.class));

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
            assertEquals("Object not found Id: test, Type: UserResponse", ex.getMessage());
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

    @Test
    void whenCallSaveThenSuccess() {

        final var request = generateMock(CreateUserRequest.class);

        when(mapper.fromRequest(any(CreateUserRequest.class))).thenReturn( new UserDomain());
        when(encoder.encode(anyString())).thenReturn("encoded");
        when(repository.save(any(UserDomain.class))).thenReturn(new UserDomain());
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        service.save(request);

        verify(mapper, times(1)).fromRequest(request);
        verify(encoder, times(1)).encode(request.password());
        verify(repository, times(1)).findByEmail(request.email());

    }

    @Test
    void whenCallSaveWithEmailAlreadyExistsThrowDataIntegrityViolationException() {

        final var request = generateMock(CreateUserRequest.class);
        final var entity = generateMock(UserDomain.class);

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(entity));

        try {
            service.save(request);
        } catch (Exception ex) {
           assertEquals(DataIntegrityViolationException.class, ex.getClass());
           assertEquals("Email: ["+request.email()+"] already exists", ex.getMessage());
        }

        verify(repository, times(1)).findByEmail(request.email());
        verify(mapper, times(0)).fromRequest(request);
        verify(encoder, times(0)).encode(request.password());
        verify(repository, times(0)).save(any(UserDomain.class));
    }

    @Test
    void whenCallUpdateWithInvalidIdThenThrowObjectNotFound() {

        final var request = generateMock(UpdateUserRequest.class);

        when(repository.findById(anyString())).thenReturn(Optional.empty());

        try {
            service.update(request, "test");

        }catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass() );
            assertEquals( "Object not found Id: test, Type: UserResponse" , ex.getMessage());
        }

        verify(repository, times(1)).findById(anyString());
        verify(mapper, times(0)).update(any(request.getClass()), any(UserDomain.class));
        verify(repository, times(0)).save(any(UserDomain.class));
        verify(encoder, times(0)).encode(request.password());

    }

    @Test
    void  whenCallUpdateWithEmailAlreadyExistsThrowDataIntegrityViolationException() {

        final var request = generateMock(UpdateUserRequest.class);
        final var entity = generateMock(UserDomain.class);

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(entity));
        when(repository.findById(anyString())).thenReturn(Optional.of(entity));

        try {
            service.update(request, "test");
        }catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("Email: ["+request.email()+"] already exists", ex.getMessage());

        }

        verify(repository, times(1)).findByEmail(request.email());
        verify(repository,times(1)).findById(anyString());
        verify(mapper, times(0)).update(any(), any());
        verify(encoder, times(0)).encode(anyString());
        verify(repository, times(0)).save(any(UserDomain.class));
    }

    @Test
    void whenCallUpdateWithValidArgumentsThenReturnSuccess() {
        final var id = "test";
        final var request = generateMock(UpdateUserRequest.class);
        final var entity = generateMock(UserDomain.class).withId(id);

        when(repository.findById(anyString())).thenReturn(Optional.of(entity));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(entity));
        when(mapper.update(any(), any())).thenReturn(entity);
        when(repository.save(any(UserDomain.class))).thenReturn(entity);

        service.update(request, id);

        verify(repository).findById(anyString());
        verify(repository).findByEmail(request.email());
        verify(mapper).update(request, entity);
        verify(encoder).encode(request.password());
        verify(repository).save(any(UserDomain.class));
        verify(mapper).fromEntity(any(UserDomain.class));

    }




}