package com.kozich.finance.account_service.mapper;

import com.kozich.finance.account_service.core.dto.AccountDTO;
import com.kozich.finance.account_service.model.AccountEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountMapper {

    @Mapping(target = "dtCreate", qualifiedByName = "mapLongToLocalDateTime", source = "dtCreate")
    @Mapping(target = "dtUpdate", qualifiedByName = "mapLongToLocalDateTime", source = "dtUpdate")
    AccountEntity accountDTOToAccountEntity(AccountDTO accountDTO);

    @Named("mapLongToLocalDateTime")
    default LocalDateTime mapLongToLocalDateTime(Long dateTime) {
        if (dateTime == null) {
            return null;
        }
        return Instant.ofEpochMilli(dateTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @Mapping(target = "dtCreate", qualifiedByName = "mapLocalDateTimeToLong", source = "dtCreate")
    @Mapping(target = "dtUpdate", qualifiedByName = "mapLocalDateTimeToLong", source = "dtUpdate")
    AccountDTO accountEntityToAccountDTO(AccountEntity accountEntity);


    @Named("mapLocalDateTimeToLong")
    default Long mapLocalDateTimeToLong(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
