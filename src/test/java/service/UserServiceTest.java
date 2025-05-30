package service;

import com.stockportfolio.dto.LoginResponse;
import com.stockportfolio.dto.RegistrationRequest;
import com.stockportfolio.entity.User;
import com.stockportfolio.exception.InvalidEmailFormatException;
import com.stockportfolio.exception.UserAlreadyExistsException;
import com.stockportfolio.exception.UserNotFoundException;
import com.stockportfolio.repository.UserRepository;
import com.stockportfolio.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private RegistrationRequest request;

    @BeforeEach
    void setUp() {
        request = new RegistrationRequest();
        request.setUsername("john_doe");
        request.setPassword("password123");
        request.setEmail("john@example.com");
    }

    @Test
    void testSaveUser_Success() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(null);
        when(userRepository.findByUsername("john_doe")).thenReturn(null);

        User savedUser = new User();
        savedUser.setUsername("john_doe");
        savedUser.setPassword("password123");
        savedUser.setemail("john@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.saveUser(request);

        assertEquals("john_doe", result.getUsername());
        assertEquals("password123", result.getPassword());
        assertEquals("john@example.com", result.getemail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSaveUser_InvalidEmail() {
        request.setEmail("invalid-email");

        assertThrows(InvalidEmailFormatException.class, () -> userService.saveUser(request));
    }

    @Test
    void testSaveUser_EmailAlreadyExists() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(new User());

        assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(request));
    }

    @Test
    void testSaveUser_UsernameAlreadyExists() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(null);
        when(userRepository.findByUsername("john_doe")).thenReturn(new User());

        assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(request));
    }

    @Test
    void testLogin_Success() {
        User user = new User();
        user.setId((int) 1L);
        user.setUsername("john_doe");
        user.setPassword("password123");
        user.setemail("john@example.com");

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        LoginResponse response = userService.login("john@example.com", "password123");

        assertEquals(1L, response.getId());
        assertEquals("john_doe", response.getUsername());
        assertEquals("john@example.com", response.getemail());
    }

    @Test
    void testLogin_InvalidCredentials() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.login("john@example.com", "wrongpass"));
    }

    @Test
    void testLogin_WrongPassword() {
        User user = new User();
        user.setPassword("correctpass");

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        assertThrows(UserNotFoundException.class, () -> userService.login("john@example.com", "wrongpass"));
    }
}
