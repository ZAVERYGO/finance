package com.kozich.finance.user_service.controller.http;

import com.kozich.finance.user_service.core.dto.UserFullDTO;
import com.kozich.finance.user_service.model.UserEntity;
import com.kozich.finance.user_service.service.UserHolder;
import com.kozich.finance.user_service.service.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.util.UUID;


@RestController
@Validated
@RequestMapping("/feign")
public class FeignController {

    private final UserService userService;

    public FeignController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/byEmail")
    @ResponseStatus(HttpStatus.OK)
    public UserFullDTO getUserByEmail(@RequestParam(value = "mail") String mail){

        UserEntity userEntity = userService.getByEmail(mail);


        return new UserFullDTO()
                .setUuid(userEntity.getUuid())
                .setEmail(userEntity.getEmail())
                .setRole(userEntity.getRole())
                .setFio(userEntity.getFio())
                .setPassword(userEntity.getPassword())
                .setStatus(userEntity.getStatus())
                .setDtCreate(userEntity.getDtCreate().toInstant(ZoneOffset.UTC).toEpochMilli())
                .setDtUpdate(userEntity.getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli());

    }

    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public UserFullDTO getUserById(@RequestParam(value = "uuid") UUID uuid){

        UserEntity userEntity = userService.getById(uuid);


        return new UserFullDTO()
                .setUuid(userEntity.getUuid())
                .setEmail(userEntity.getEmail())
                .setRole(userEntity.getRole())
                .setFio(userEntity.getFio())
                .setPassword(userEntity.getPassword())
                .setStatus(userEntity.getStatus())
                .setDtCreate(userEntity.getDtCreate().toInstant(ZoneOffset.UTC).toEpochMilli())
                .setDtUpdate(userEntity.getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli());

    }
}
