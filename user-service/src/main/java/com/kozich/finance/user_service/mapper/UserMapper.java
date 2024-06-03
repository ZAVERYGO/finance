package com.kozich.finance.user_service.mapper;

import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.model.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserEntity userEntityToUserDTO(UserDTO userDTO);

    UserDTO userEntityToUserDTO(UserEntity userEntity);

}
