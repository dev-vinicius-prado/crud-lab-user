package br.com.devvinnas.crud_lab_user.security;

import br.com.devvinnas.crud_lab_user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String cpf;
    private String phoneNumber;
    private LocalDateTime birthDate;
    private Role role;
}