package com.kozich.finance.audit_service.entity;

import com.kozich.finance_storage.core.enums.AuditType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="audit",schema = "app")
public class AuditEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Column(name = "user_uuid")
    private UUID user;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AuditType type;

    @Column(name = "id")
    private String id;

    public AuditEntity() {
    }

    public AuditEntity(UUID uuid, LocalDateTime dtCreate, UUID user, String text, AuditType type, String id) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.user = user;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public AuditEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public AuditEntity setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public UUID getUser() {
        return user;
    }

    public AuditEntity setUser(UUID user) {
        this.user = user;
        return this;
    }

    public String getText() {
        return text;
    }

    public AuditEntity setText(String text) {
        this.text = text;
        return this;
    }

    public AuditType getType() {
        return type;
    }

    public AuditEntity setType(AuditType type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public AuditEntity setId(String id) {
        this.id = id;
        return this;
    }
}
