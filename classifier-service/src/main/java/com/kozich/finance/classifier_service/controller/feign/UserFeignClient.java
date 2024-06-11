package com.kozich.finance.classifier_service.controller.feign;

import com.kozich.finance.classifier_service.core.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://localhost:8084/cabinet")
public interface UserFeignClient {

    @GetMapping("/me")
    UserDTO getMyCabinet(@RequestHeader("Authorization") String token);

}
