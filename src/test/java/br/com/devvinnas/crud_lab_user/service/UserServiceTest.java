package br.com.devvinnas.crud_lab_user.service;

import br.com.devvinnas.crud_lab_user.domain.User;
import br.com.devvinnas.crud_lab_user.dto.CreateUserDTO;
import br.com.devvinnas.crud_lab_user.dto.UpdateUserDTO;
import br.com.devvinnas.crud_lab_user.dto.UserResponseDTO;
import br.com.devvinnas.crud_lab_user.exception.UserAlreadyExistsException;
import br.com.devvinnas.crud_lab_user.exception.UserNotFoundException;
import br.com.devvinnas.crud_lab_user.mapper.UserMapper;
import br.com.devvinnas.crud_lab_user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private CreateUserDTO createUserDTO;
    private UpdateUserDTO updateUserDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .cpf("12345678900")
                .password("password")
                .birthDate(LocalDateTime.now().minusYears(20))
                .phoneNumber("11987654321")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        createUserDTO = CreateUserDTO.builder()
                .name("Test User")
                .email("test@example.com")
                .cpf("12345678900")
                .password("password")
                .birthDate(LocalDateTime.now().minusYears(20))
                .phoneNumber("11987654321")
                .build();

        updateUserDTO = UpdateUserDTO.builder()
                .name("Updated User")
                .email("updated@example.com")
                .phoneNumber("11999999999")
                .build();

        userResponseDTO = UserResponseDTO.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .cpf("12345678900")
                .birthDate(LocalDateTime.now().minusYears(20))
                .phoneNumber("11987654321")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should create a new user successfully")
    void shouldCreateNewUserSuccessfully() {
        when(userRepository.existsByEmail(createUserDTO.getEmail())).thenReturn(false);
        when(userRepository.existsByCpf(createUserDTO.getCpf())).thenReturn(false);
        when(userMapper.toEntity(createUserDTO)).thenReturn(user);
        when(passwordEncoder.encode(createUserDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.createUser(createUserDTO);

        assertNotNull(result);
        assertEquals(userResponseDTO.getEmail(), result.getEmail());
        verify(userRepository, times(1)).existsByEmail(createUserDTO.getEmail());
        verify(userRepository, times(1)).existsByCpf(createUserDTO.getCpf());
        verify(userMapper, times(1)).toEntity(createUserDTO);
        verify(passwordEncoder, times(1)).encode(createUserDTO.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toResponseDTO(user);
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException when email already exists")
    void shouldThrowUserAlreadyExistsExceptionWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(createUserDTO.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(createUserDTO));
        verify(userRepository, times(1)).existsByEmail(createUserDTO.getEmail());
        verify(userRepository, never()).existsByCpf(anyString());
        verify(userMapper, never()).toEntity(any(CreateUserDTO.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException when CPF already exists")
    void shouldThrowUserAlreadyExistsExceptionWhenCpfAlreadyExists() {
        when(userRepository.existsByEmail(createUserDTO.getEmail())).thenReturn(false);
        when(userRepository.existsByCpf(createUserDTO.getCpf())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(createUserDTO));
        verify(userRepository, times(1)).existsByEmail(createUserDTO.getEmail());
        verify(userRepository, times(1)).existsByCpf(createUserDTO.getCpf());
        verify(userMapper, never()).toEntity(any(CreateUserDTO.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should find user by ID successfully")
    void shouldFindUserByIdSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(userResponseDTO.getId(), result.getId());
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toResponseDTO(user);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when user not found by ID")
    void shouldThrowUserNotFoundExceptionWhenUserNotFoundById() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, never()).toResponseDTO(any(User.class));
    }

    @Test
    @DisplayName("Should find all users successfully")
    void shouldFindAllUsersSuccessfully() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        List<UserResponseDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(userResponseDTO.getEmail(), result.get(0).getEmail());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).toResponseDTO(user);
    }

    @Test
    @DisplayName("Should return empty list when no users found")
    void shouldReturnEmptyListWhenNoUsersFound() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserResponseDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, never()).toResponseDTO(any(User.class));
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(updateUserDTO.getEmail())).thenReturn(false);
        doNothing().when(userMapper).updateEntityFromDTO(updateUserDTO, user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.updateUser(1L, updateUserDTO);

        assertNotNull(result);
        assertEquals(userResponseDTO.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).existsByEmail(updateUserDTO.getEmail());
        verify(userMapper, times(1)).updateEntityFromDTO(updateUserDTO, user);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toResponseDTO(user);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when updating non-existent user")
    void shouldThrowUserNotFoundExceptionWhenUpdatingNonExistentUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, updateUserDTO));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userMapper, never()).updateEntityFromDTO(any(UpdateUserDTO.class), any(User.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException when updating user with existing email")
    void shouldThrowUserAlreadyExistsExceptionWhenUpdatingUserWithExistingEmail() {
        User anotherUser = User.builder().id(2L).email("updated@example.com").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(updateUserDTO.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.updateUser(1L, updateUserDTO));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).existsByEmail(updateUserDTO.getEmail());
        verify(userMapper, never()).updateEntityFromDTO(any(UpdateUserDTO.class), any(User.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUserSuccessfully() {
        long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);

        assertDoesNotThrow(() -> userService.deleteUser(userId));

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when deleting non-existent user")
    void shouldThrowUserNotFoundExceptionWhenDeletingNonExistentUser() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, never()).delete(any(User.class));
    }
}