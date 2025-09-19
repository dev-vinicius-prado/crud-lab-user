package br.com.devvinnas.crud_lab_user.mapper;

import br.com.devvinnas.crud_lab_user.domain.User;
import br.com.devvinnas.crud_lab_user.dto.CreateUserDTO;
import br.com.devvinnas.crud_lab_user.dto.UpdateUserDTO;
import br.com.devvinnas.crud_lab_user.dto.UserResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(CreateUserDTO createUserDTO);

    @Mapping(target = "password", ignore = true)
    void updateEntityFromDTO(UpdateUserDTO updateUserDTO, @MappingTarget User user);

    UserResponseDTO toResponseDTO(User user);
}