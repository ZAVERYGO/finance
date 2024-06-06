package com.kozich.finance.user_service.controller.http;

import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.service.api.CabinetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cabinet")
public class CabinetController {

    private final CabinetService cabinetService;

    public CabinetController(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDTO UserDTO){

        cabinetService.registerUser(UserDTO);

    }

    @GetMapping("/verification")
    @ResponseStatus(HttpStatus.OK)
    public void verifyUser(@RequestParam(value = "code") String code,
                           @RequestParam(value = "mail") String mail){

        cabinetService.verifyUser(code, mail);

    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String loginUser(@RequestBody UserDTO UserDTO){

        return cabinetService.loginUser(UserDTO);

    }
}
