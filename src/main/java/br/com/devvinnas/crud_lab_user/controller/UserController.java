package br.com.devvinnas.crud_lab_user.controller;

import br.com.devvinnas.crud_lab_user.dto.CreateUserDTO;
import br.com.devvinnas.crud_lab_user.dto.UpdateUserDTO;
import br.com.devvinnas.crud_lab_user.dto.UserResponseDTO;
import br.com.devvinnas.crud_lab_user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "API para gerenciamento de usuários do laboratório")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Criar novo usuário")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        UserResponseDTO userResponse = userService.createUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        Page<UserResponseDTO> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserDTO updateUserDTO) {
        UserResponseDTO updatedUser = userService.updateUser(id, updateUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-status")
    @Operation(summary = "Alternar status do usuário (ativo/inativo)")
    public ResponseEntity<UserResponseDTO> toggleUserStatus(@PathVariable Long id) {
        UserResponseDTO updatedUser = userService.toggleUserStatus(id);
        return ResponseEntity.ok(updatedUser);
    }
}