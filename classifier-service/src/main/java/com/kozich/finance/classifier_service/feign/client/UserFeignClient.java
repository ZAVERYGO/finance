package com.kozich.finance.classifier_service.feign.client;

import com.kozich.finance.classifier_service.core.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@FeignClient(name = "user-service", url = "${url.users}")
public interface UserFeignClient {

    @GetMapping("/byEmail")
    @ResponseStatus(HttpStatus.OK)
    UserDTO getUserByEmail(@RequestParam(value = "mail") String mail);

}
