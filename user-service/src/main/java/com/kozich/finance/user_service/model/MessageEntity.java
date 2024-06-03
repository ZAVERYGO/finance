package com.kozich.finance.user_service.model;

import com.kozich.finance.user_service.core.MessageStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "message", schema = "app")
public class MessageEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "verification_code")
    private String code;

    @Column(name = "status")
    private MessageStatus status;

    @Column(name = "dt_Create")
    private LocalDateTime dtCreate;

    @OneToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private UserEntity userUuid;

    public MessageEntity() {
    }

    public MessageEntity(UUID uuid, String code, MessageStatus status, LocalDateTime dtCreate, UserEntity userUuid) {
        this.uuid = uuid;
        this.code = code;
        this.status = status;
        this.dtCreate = dtCreate;
        this.userUuid = userUuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public MessageEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getCode() {
        return code;
    }

    public MessageEntity setCode(String code) {
        this.code = code;
        return this;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public MessageEntity setStatus(MessageStatus status) {
        this.status = status;
        return this;
    }

    public UserEntity getUserUuid() {
        return userUuid;
    }

    public MessageEntity setUserUuid(UserEntity userUuid) {
        this.userUuid = userUuid;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public MessageEntity setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }
}
