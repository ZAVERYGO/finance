package com.kozich.finance.user_service.service.impl;

import com.kozich.finance.user_service.core.enums.MessageStatus;
import com.kozich.finance.user_service.core.dto.MessageDTO;
import com.kozich.finance.user_service.entity.MessageEntity;
import com.kozich.finance.user_service.entity.UserEntity;
import com.kozich.finance.user_service.repository.MessageRepository;
import com.kozich.finance.user_service.service.api.MessageService;
import com.kozich.finance.user_service.service.api.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageServiceImpl(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }


    @Override
    public List<MessageEntity> getAllByStatus(MessageStatus status) {
        return messageRepository.findAllByStatus(status);
    }

    @Transactional
    @Override
    public MessageEntity create(MessageDTO messageDTO) {

        LocalDateTime date = LocalDateTime.now();

        MessageEntity messageEntity = new MessageEntity()
                .setUuid(UUID.randomUUID())
                .setUserUuid(userService.getByEmail(messageDTO.getEmail()))
                .setDtCreate(date)
                .setStatus(messageDTO.getStatus())
                .setCode(messageDTO.getCode());

        return messageRepository.saveAndFlush(messageEntity);
    }

    @Override
    public MessageEntity getByUser(UserEntity userEntity) {
        return messageRepository.findByUserUuid(userEntity)
                .orElseThrow(() -> new IllegalArgumentException("Не существует пользователя"));
    }

    @Transactional
    @Override
    public MessageEntity update(MessageDTO messageDTO) {

        UserEntity userEntity = userService.getByEmail(messageDTO.getEmail());
        Optional<MessageEntity> messageEntity = messageRepository.findByUserUuid(userEntity);
        MessageEntity message = messageEntity.orElseThrow(() -> new IllegalArgumentException("Не существует сообщения"));

        if (messageDTO.getCode() != null) {
            message.setCode(messageDTO.getCode());
        }

        return messageRepository.saveAndFlush(message.setStatus(messageDTO.getStatus()));
    }

}

