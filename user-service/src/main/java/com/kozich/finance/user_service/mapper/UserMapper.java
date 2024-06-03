package com.kozich.finance.user_service.mapper;

import com.kozich.finance.user_service.core.dto.UserCUDTO;
import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.model.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mapping(target = "dtCreate", qualifiedByName = "mapLongToLocalDateTime", source = "userDTO.dtCreate")
    @Mapping(target = "dtUpdate", qualifiedByName = "mapLongToLocalDateTime", source = "userDTO.dtUpdate")
    UserEntity userDTOToUserEntity(UserDTO userDTO);

    @Named("mapLongToLocalDateTime")
    default LocalDateTime mapLongToLocalDateTime(Long dtCreate) {
        return Instant.ofEpochMilli(dtCreate).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    @Mapping(target = "dtCreate", qualifiedByName = "mapLocalDateTimeToLong", source = "userEntity.dtCreate")
    @Mapping(target = "dtUpdate", qualifiedByName = "mapLocalDateTimeToLong", source = "userEntity.dtUpdate")
    UserDTO userEntityToUserDTO(UserEntity userEntity);


    @Named("mapLocalDateTimeToLong")
    default Long mapLocalDateTimeToLong(LocalDateTime dtCreate) {
        return dtCreate.getLong(ChronoField.MILLI_OF_SECOND);
    }
    UserCUDTO userEntityToUserCUDTO(UserEntity userEntity);

    UserEntity userCUDTOToUserEntity(UserCUDTO userCUDTO);

}
