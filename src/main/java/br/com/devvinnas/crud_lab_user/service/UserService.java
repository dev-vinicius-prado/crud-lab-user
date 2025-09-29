package br.com.devvinnas.crud_lab_user.service;

import br.com.devvinnas.crud_lab_user.domain.User;
import br.com.devvinnas.crud_lab_user.dto.CreateUserDTO;
import br.com.devvinnas.crud_lab_user.dto.UpdateUserDTO;
import br.com.devvinnas.crud_lab_user.dto.UserResponseDTO;
import br.com.devvinnas.crud_lab_user.exception.UserAlreadyExistsException;
import br.com.devvinnas.crud_lab_user.exception.UserNotFoundException;
import br.com.devvinnas.crud_lab_user.mapper.UserMapper;
import br.com.devvinnas.crud_lab_user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO createUser(CreateUserDTO createUserDTO) {
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email já cadastrado");
        }
        if (userRepository.existsByCpf(createUserDTO.getCpf())) {
            throw new UserAlreadyExistsException("CPF já cadastrado");
        }

        User user = userMapper.toEntity(createUserDTO);
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        return userMapper.toResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        if (updateUserDTO.getEmail() != null && 
            !updateUserDTO.getEmail().equals(user.getEmail()) && 
            userRepository.existsByEmail(updateUserDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email já cadastrado");
        }

        userMapper.updateEntityFromDTO(updateUserDTO, user);

        if (updateUserDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserResponseDTO toggleUserStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        
        user.setActive(!user.isActive());
        User updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }
}