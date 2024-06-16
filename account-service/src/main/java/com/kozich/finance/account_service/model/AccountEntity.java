package com.kozich.finance.account_service.model;

import com.kozich.finance.account_service.core.AccountType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account", schema = "app")
public class AccountEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "balance")
    private Integer balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AccountType type;

    @Column(name = "dt_Create")
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_Update")
    private LocalDateTime dtUpdate;

    @Column(name = "currency_uuid")
    private UUID currencyUuid;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "accountEntity")
    private List<OperationEntity> operationEntityList;


    public AccountEntity(UUID uuid, String title, String description,
                         Integer balance, AccountType type, LocalDateTime dtCreate,
                         LocalDateTime dtUpdate, UUID currencyUuid, String email) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.balance = balance;
        this.type = type;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.currencyUuid = currencyUuid;
        this.email = email;
    }

    public AccountEntity() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public AccountEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AccountEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AccountEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getBalance() {
        return balance;
    }

    public AccountEntity setBalance(Integer balance) {
        this.balance = balance;
        return this;
    }

    public AccountType getType() {
        return type;
    }

    public AccountEntity setType(AccountType type) {
        this.type = type;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public AccountEntity setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public AccountEntity setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public UUID getCurrencyUuid() {
        return currencyUuid;
    }

    public AccountEntity setCurrencyUuid(UUID currencyUuid) {
        this.currencyUuid = currencyUuid;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AccountEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<OperationEntity> getOperationEntityList() {
        return operationEntityList;
    }

    public AccountEntity setOperationEntityList(List<OperationEntity> operationEntityList) {
        this.operationEntityList = operationEntityList;
        return this;
    }
}
