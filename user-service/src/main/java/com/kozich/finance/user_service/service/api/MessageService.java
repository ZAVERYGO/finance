package com.kozich.finance.user_service.service.api;

import com.kozich.finance.user_service.core.MessageStatus;
import com.kozich.finance.user_service.core.dto.MessageDTO;
import com.kozich.finance.user_service.model.MessageEntity;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    List<MessageEntity> getAllByStatus(MessageStatus status);
    MessageEntity create(MessageDTO messageDTO);

    MessageEntity getById(UUID uuid);

    MessageEntity update(MessageDTO messageDTO);

}
