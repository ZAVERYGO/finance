package com.kozich.finance.account_service.controller.http;

import com.kozich.finance.account_service.core.dto.AccountCUDTO;
import com.kozich.finance.account_service.core.dto.AccountDTO;
import com.kozich.finance.account_service.core.dto.PageAccountDTO;
import com.kozich.finance.account_service.mapper.AccountMapper;
import com.kozich.finance.account_service.model.AccountEntity;
import com.kozich.finance.account_service.model.OperationEntity;
import com.kozich.finance.account_service.service.api.AccountService;
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
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody AccountCUDTO accountCUDTO){

        accountService.create(accountCUDTO);

    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PageAccountDTO getPage(@NonNull @PositiveOrZero @RequestParam(value = "page") Integer page,
                                  @NonNull @Positive @RequestParam(value = "size") Integer size){

        Page<AccountEntity> pageEntity = accountService.getPage(page, size);
        PageAccountDTO pageAccountDTO = new PageAccountDTO()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<AccountEntity> contentEntity = pageEntity.getContent();
        List<AccountDTO> contentDTO = new ArrayList<>();

        for (AccountEntity accountEntity : contentEntity) {
            contentDTO.add(accountMapper.accountEntityToAccountDTO(accountEntity));
        }
        return pageAccountDTO.setContent(contentDTO);

    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO get(@PathVariable(name = "uuid") UUID uuid){

        AccountEntity byId = accountService.getById(uuid);
        return accountMapper.accountEntityToAccountDTO(byId);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable(value = "uuid") UUID uuid,
                                           @PathVariable(value = "dt_update") Long dtUpdate,
                                           @RequestBody AccountCUDTO accountCUDTO){

        accountService.update(uuid, accountCUDTO, dtUpdate);

    }
}
