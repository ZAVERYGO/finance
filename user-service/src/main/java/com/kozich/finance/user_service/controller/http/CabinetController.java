package com.kozich.finance.user_service.controller.http;

import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.service.api.CabinetService;
import com.kozich.finance.user_service.service.api.UserService;
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
    public void create(@RequestBody UserDTO UserDTO){
        cabinetService.registerUser(UserDTO);
    }

}
