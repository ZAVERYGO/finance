package com.kozich.finance.user_service.scheduling.job;

import com.kozich.finance.user_service.core.dto.MessageDTO;
import com.kozich.finance.user_service.core.enums.MessageStatus;
import com.kozich.finance.user_service.entity.MessageEntity;
import com.kozich.finance.user_service.mapper.MessageMapper;
import com.kozich.finance.user_service.service.api.MessageSenderService;
import com.kozich.finance.user_service.service.api.MessageService;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageSendJob {

    private final MessageSenderService messageSender;
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    public MessageSendJob(MessageSenderService messageSenderService, MessageService messageService, MessageMapper messageMapper) {
        this.messageSender = messageSenderService;
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    public void start() {

        List<MessageEntity> allByStatus = messageService.getAllByStatus(MessageStatus.LOADED);

        if (allByStatus == null) {
            return;
        }

        for (MessageEntity mail : allByStatus) {
            try {
                messageSender.sendEmail(mail.getUserUuid().getEmail(), mail.getCode());
                MessageDTO messageDTO = messageMapper.messageEntityTOMessageDTO(mail);
                messageDTO.setStatus(MessageStatus.OK);
                messageService.update(messageDTO);
            } catch (MailSendException e) {
                MessageDTO messageDTO = messageMapper.messageEntityTOMessageDTO(mail);
                messageDTO.setStatus(MessageStatus.ERROR);
                messageService.update(messageDTO);
            }
        }
    }

}
