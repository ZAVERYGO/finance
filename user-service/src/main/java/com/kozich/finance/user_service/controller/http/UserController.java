package com.kozich.finance.user_service.controller.http;

import com.kozich.finance.user_service.core.dto.PageUserDTO;
import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.mapper.UserMapper;
import com.kozich.finance.user_service.model.UserEntity;
import com.kozich.finance.user_service.service.api.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getById(@PathVariable(value = "uuid") UUID uuid){

        UserEntity userEntity = userService.getById(uuid);

        return userMapper.userEntityToUserDTO(userEntity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageUserDTO getPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                               @RequestParam(value = "size", defaultValue = "20") Integer size){

        Page<UserEntity> pageEntity = userService.getPage(page, size);

        PageUserDTO pageUserDTO = new PageUserDTO()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<UserEntity> contentEntity = pageEntity.getContent();
        List<UserDTO> contentDTO = new ArrayList<>();

        for (UserEntity userEntity : contentEntity) {
            contentDTO.add(userMapper.userEntityToUserDTO(userEntity));
        }

        return pageUserDTO.setContent(contentDTO);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserDTO UserDTO){
        userService.create(UserDTO);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@RequestBody UserDTO UserDTO,
                       @PathVariable(value = "uuid") UUID uuid,
                       @PathVariable(value = "dt_update") Long dtUpdate){
        userService.update(uuid, UserDTO, dtUpdate);
    }
}
