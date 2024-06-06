package com.kozich.finance.user_service.service.api;

import com.kozich.finance.user_service.core.MessageStatus;
import com.kozich.finance.user_service.core.dto.MessageDTO;
import com.kozich.finance.user_service.model.MessageEntity;
import com.kozich.finance.user_service.model.UserEntity;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    List<MessageEntity> getAllByStatus(MessageStatus status);
    MessageEntity create(MessageDTO messageDTO);

    MessageEntity getById(UUID uuid);

    MessageEntity getByUser(UserEntity userEntity);

    MessageEntity update(MessageDTO messageDTO);

}
