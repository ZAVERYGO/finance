package com.kozich.finance.audit_service.controller.http;

import com.kozich.finance.audit_service.controller.feign.UserFeignClient;
import com.kozich.finance.audit_service.core.dto.*;
import com.kozich.finance.audit_service.mapper.AuditMapper;
import com.kozich.finance.audit_service.entity.AuditEntity;
import com.kozich.finance.audit_service.util.UserHolder;
import com.kozich.finance.audit_service.service.api.AuditService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final AuditService auditService;
    private final AuditMapper auditMapper;
    private final UserFeignClient userFeignClient;

    public AuditController(AuditService auditService, AuditMapper auditMapper, UserFeignClient userFeignClient) {
        this.auditService = auditService;
        this.auditMapper = auditMapper;
        this.userFeignClient = userFeignClient;
    }

    @GetMapping
    public PageDTO getPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "20") Integer size) {

        Page<AuditEntity> entities = auditService.getPage(page, size);

        PageDTO pageDTO = new PageDTO()
                .setNumber(entities.getNumber())
                .setSize(entities.getSize())
                .setLast(entities.isLast())
                .setFirst(entities.isFirst())
                .setTotalPages(entities.getTotalPages())
                .setTotalElements(entities.getTotalElements())
                .setNumberOfElements(entities.getNumberOfElements());

        List<AuditEntity> content = entities.getContent();
        List<AuditDTO> list = new ArrayList<>();

        for (AuditEntity auditEntity : content) {
            UserDTO userById = userFeignClient.getUserById(auditEntity.getUser());
            UserAuditDTO userAuditDTO = new UserAuditDTO()
                    .setFio(userById.getFio())
                    .setUuid(userById.getUuid())
                    .setMail(userById.getEmail())
                    .setRole(userById.getRole());

            AuditDTO auditDTO = auditMapper.auditEntityTOAuditDTO(auditEntity).setUser(userAuditDTO);

            list.add(auditDTO);
        }

        return pageDTO.setContent(list);
    }

    @GetMapping(value = "/{uuid}")
    public AuditDTO getById(@PathVariable(value = "uuid") UUID uuid) {

        AuditEntity entity = auditService.get(uuid);

        AuditDTO auditDTO = auditMapper.auditEntityTOAuditDTO(entity);

        UserDTO userDTO = userFeignClient.getUserById(entity.getUser());

        UserAuditDTO userAuditDTO = new UserAuditDTO()
                .setUuid(userDTO.getUuid())
                .setRole(userDTO.getRole())
                .setMail(userDTO.getEmail())
                .setFio(userDTO.getFio());

        auditDTO.setUser(userAuditDTO);

        return auditDTO;
    }

}