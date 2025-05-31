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
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_shouldThrowInvalidEmailFormatException() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("invalidemail");
        request.setUsername("user1");
        request.setPassword("pass");

        InvalidEmailFormatException ex = assertThrows(InvalidEmailFormatException.class,
                () -> userService.saveUser(request));
        assertEquals("Invalid email format.", ex.getMessage());
    }

    @Test
    void saveUser_shouldThrowUserAlreadyExistsException_whenEmailExists() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("test@example.com");
        request.setUsername("user1");
        request.setPassword("pass");

        when(userRepository.findByEmail("test@example.com")).thenReturn(new User());

        UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class,
                () -> userService.saveUser(request));
        assertEquals("Email already registered", ex.getMessage());
    }

    @Test
    void saveUser_shouldSaveUserSuccessfully() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("test@example.com");
        request.setUsername("user1");
        request.setPassword("pass");

        when(userRepository.findByEmail("test@example.com")).thenReturn(null);
        when(userRepository.findByUsername("user1")).thenReturn(null);

        User savedUser = new User();
        savedUser.setId((int) 1L);
        savedUser.setUsername("user1");
        savedUser.setemail("test@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.saveUser(request);
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
        assertEquals("test@example.com", result.getemail());
    }

    @Test
    void login_shouldThrowUserNotFoundException_whenInvalid() {
        when(userRepository.findByEmail("wrong@example.com")).thenReturn(null);
        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> userService.login("wrong@example.com", "pass"));
        assertEquals("Invalid email or password", ex.getMessage());
    }

    @Test
    void login_shouldReturnLoginResponse() {
        User user = new User();
        user.setId((int) 1L);
        user.setUsername("user1");
        user.setPassword("pass");
        user.setemail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        LoginResponse response = userService.login("test@example.com", "pass");
        assertEquals(1L, response.getId());
        assertEquals("user1", response.getUsername());
        assertEquals("test@example.com", response.getemail());
    }
}
