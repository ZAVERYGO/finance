package com.kozich.finance.account_service.controller.http;

import com.kozich.finance.account_service.core.dto.OperationCUDTO;
import com.kozich.finance.account_service.core.dto.OperationDTO;
import com.kozich.finance.account_service.entity.OperationEntity;
import com.kozich.finance.account_service.mapper.OperationMapper;
import com.kozich.finance.account_service.service.api.OperationService;
import com.kozich.finance.account_service.util.UserHolder;
import com.kozich.finance_storage.core.dto.PageDTO;
import com.kozich.finance_storage.core.enums.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/account")
public class OperationController {

    private final OperationService operationService;
    private final OperationMapper operationMapper;
    private final UserHolder userHolder;

    public OperationController(OperationService operationService, OperationMapper operationMapper, UserHolder userHolder) {
        this.operationService = operationService;
        this.operationMapper = operationMapper;
        this.userHolder = userHolder;
    }

    @PostMapping("/{uuid}/operation")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody OperationCUDTO operationCUDTO,
                       @PathVariable(name = "uuid") UUID uuid) {
        operationService.create(operationCUDTO, uuid);
    }

    @GetMapping("/{uuid}/operation")
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<OperationDTO> getPage(@NonNull @PositiveOrZero @RequestParam(value = "page") Integer page,
                                         @NonNull @Positive @RequestParam(value = "size") Integer size,
                                         @PathVariable(name = "uuid") UUID uuid) {

        String userRole = userHolder.getUserRole();

        Page<OperationEntity> pageEntity = userRole.equals(UserRole.ROLE_ADMIN.name())
                ? operationService.getPage(page, size, uuid)
                : operationService.getPage(page, size, uuid, userHolder.getUser().getUsername());

        PageDTO<OperationDTO> pageOperationDTO = new PageDTO<OperationDTO>()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<OperationEntity> contentEntity = pageEntity.getContent();
        List<OperationDTO> contentDTO = new ArrayList<>();

        for (OperationEntity operationEntity : contentEntity) {
            contentDTO.add(operationMapper.operationEntityToOperationDTO(operationEntity));
        }
        return pageOperationDTO.setContent(contentDTO);
    }

    @PutMapping("/{uuid}/operation/{uuid_operation}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable(name = "uuid") UUID uuid,
                       @PathVariable(name = "uuid_operation") UUID uuidOperation,
                       @PathVariable(name = "dt_update") Long dtUpdate,
                       @RequestBody OperationCUDTO operationCUDTO) {
        operationService.update(uuid, uuidOperation, operationCUDTO, dtUpdate);
    }

    @DeleteMapping("/{uuid}/operation/{uuid_operation}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(name = "uuid") UUID uuid,
                       @PathVariable(name = "uuid_operation") UUID uuidOperation,
                       @PathVariable(name = "dt_update") Long dtUpdate) {
        operationService.delete(uuid, uuidOperation, dtUpdate);
    }
}
