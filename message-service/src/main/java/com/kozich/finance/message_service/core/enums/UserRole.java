package com.kozich.finance.message_service.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {

    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER"),
    ROLE_MANAGER("MANAGER");

    private final String content;

    UserRole(String content) {
        this.content = content;
    }

    @JsonValue
    public String getContentType() {
        return content;
    }

    @JsonCreator
    public static UserRole fromValue(String value) {
        for (UserRole content : values()) {
            String currentContact = content.getContentType();
            if (currentContact.equals(value)) {
                return content;
            }
        }
        throw new IllegalArgumentException("Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз");

    }
}
