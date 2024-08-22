package com.kozich.finance.user_service.service.api;

import com.kozich.finance.user_service.core.enums.MessageStatus;
import com.kozich.finance.user_service.core.dto.MessageDTO;
import com.kozich.finance.user_service.entity.MessageEntity;
import com.kozich.finance.user_service.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    List<MessageEntity> getAllByStatus(MessageStatus status);

    MessageEntity create(MessageDTO messageDTO);

    MessageEntity getByUser(UserEntity userEntity);

    MessageEntity update(MessageDTO messageDTO);

}
