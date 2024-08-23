package com.kozich.finance.classifier_service.controller.feign.client;

import com.kozich.finance.classifier_service.core.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "user-service", url = "${url.users}")
public interface UserFeignClient {

    @GetMapping("/byEmail")
    @ResponseStatus(HttpStatus.OK)
    UserDTO getUserByEmail(@RequestParam(value = "mail") String mail);

}
