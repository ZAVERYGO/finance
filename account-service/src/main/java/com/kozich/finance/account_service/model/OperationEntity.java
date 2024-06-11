package com.kozich.finance.account_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "operation", schema = "app")
public class OperationEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private Integer value;

    @Column(name = "dt_Create")
    private LocalDateTime dtCreate;

    @Column(name = "dt_Update")
    private LocalDateTime dtUpdate;

    @Column(name = "currency_uuid")
    private UUID currencyUuid;

    @Column(name = "category_uuid")
    private UUID catagoryUuid;

    @ManyToOne
    @JoinColumn(name = "account_uuid")
    private AccountEntity accountEntity;

    public OperationEntity(UUID uuid, LocalDateTime date, String description,
                           Integer value, LocalDateTime dtCreate, LocalDateTime dtUpdate,
                           UUID currencyUuid, UUID catagoryUuid, AccountEntity accountEntity) {
        this.uuid = uuid;
        this.date = date;
        this.description = description;
        this.value = value;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.currencyUuid = currencyUuid;
        this.catagoryUuid = catagoryUuid;
        this.accountEntity = accountEntity;
    }

    public OperationEntity() {
    }

    public UUID getUuid() {        return uuid;
    }

    public OperationEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public OperationEntity setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OperationEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getValue() {
        return value;
    }

    public OperationEntity setValue(Integer value) {
        this.value = value;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public OperationEntity setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public OperationEntity setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public UUID getCurrencyUuid() {
        return currencyUuid;
    }

    public OperationEntity setCurrencyUuid(UUID currencyUuid) {
        this.currencyUuid = currencyUuid;
        return this;
    }

    public UUID getCatagoryUuid() {
        return catagoryUuid;
    }

    public OperationEntity setCatagoryUuid(UUID catagoryUuid) {
        this.catagoryUuid = catagoryUuid;
        return this;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public OperationEntity setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
        return this;
    }
}
