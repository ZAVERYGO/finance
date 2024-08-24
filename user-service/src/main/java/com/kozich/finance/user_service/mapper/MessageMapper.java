package com.kozich.finance.user_service.mapper;

import com.kozich.finance.user_service.core.dto.MessageDTO;
import com.kozich.finance.user_service.entity.MessageEntity;
import com.kozich.finance.user_service.entity.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageMapper {

    @Mapping(target = "dtCreate", qualifiedByName = "mapLongToLocalDateTime", source = "dtCreate")
    MessageEntity messageDTOTOMessageEntity(MessageDTO messageDTO);

    @Named("mapLongToLocalDateTime")
    default LocalDateTime mapLongToLocalDateTime(Long dateTime) {
        if (dateTime == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(dateTime), ZoneId.systemDefault());
    }


    @Mapping(target = "dtCreate", qualifiedByName = "mapLocalDateTimeToLong", source = "dtCreate")
    @Mapping(target = "email", qualifiedByName = "getEmailByUser", source = "userUuid")
    MessageDTO messageEntityTOMessageDTO(MessageEntity messageEntity);

    @Named("mapLocalDateTimeToLong")
    default Long mapLocalDateTimeToLong(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    @Named("getEmailByUser")
    default String getEmailByUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return userEntity.getEmail();
    }

}
