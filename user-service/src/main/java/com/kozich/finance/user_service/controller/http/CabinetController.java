package com.kozich.finance.user_service.controller.http;

import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.mapper.UserMapper;
import com.kozich.finance.user_service.model.UserEntity;
import com.kozich.finance.user_service.service.UserHolder;
import com.kozich.finance.user_service.service.api.CabinetService;
import com.kozich.finance.user_service.service.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cabinet")
public class CabinetController {

    private final CabinetService cabinetService;
    private final UserHolder userHolder;
    private final UserService userService;
    private final UserMapper userMapper;

    public CabinetController(CabinetService cabinetService, UserHolder userHolder, UserService userService, UserMapper userMapper) {
        this.cabinetService = cabinetService;
        this.userHolder = userHolder;
        this.userService = userService;
        this.userMapper = userMapper;
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

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getMyCabinet(){

        UserEntity userEntity = userService.getByEmail(userHolder.getUser().getUsername());

        return userMapper.userEntityToUserDTO(userEntity);
    }
}
