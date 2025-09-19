package br.com.devvinnas.crud_lab_user.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {

    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String name;

    @Email(message = "Email deve ser válido")
    private String email;

    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    private String password;

    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDateTime birthDate;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
    private String phoneNumber;

    private Boolean active;
}