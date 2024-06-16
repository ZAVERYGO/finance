package com.kozich.finance.audit_service.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kozich.finance.audit_service.core.AuditType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(chain = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditDTO {

    private UUID uuid;

    @JsonProperty("dt_create")
    private Long dtCreate;

    @Column(name = "user")
    private UserAuditDTO user;

    @Column(name = "user_uuid")
    private String text;

    @Enumerated(EnumType.STRING)
    private AuditType type;

    @Column(name = "id")
    private String id;
}