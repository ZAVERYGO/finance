package com.kozich.finance.message_service.service.api;

import com.kozich.finance.message_service.core.dto.MessageCUDTO;
import com.kozich.finance.message_service.entity.MessageEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MessageService {

    Page<MessageEntity> getPage(Integer page, Integer size);

    MessageEntity create(MessageCUDTO messageDTO);

    MessageEntity update(MessageCUDTO messageDTO, UUID emailUuid, Long dtUpdate);

}
