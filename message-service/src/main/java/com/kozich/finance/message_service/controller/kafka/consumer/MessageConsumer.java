package com.kozich.finance.message_service.controller.kafka.consumer;

import com.kozich.finance.message_service.core.dto.MessageCUDTO;
import com.kozich.finance.message_service.core.dto.MessageSendDTO;
import com.kozich.finance.message_service.core.enums.MessageStatus;
import com.kozich.finance.message_service.entity.MessageEntity;
import com.kozich.finance.message_service.service.api.MessageSenderService;
import com.kozich.finance.message_service.service.api.MessageService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class MessageConsumer {

    private final MessageService messageService;
    private final MessageSenderService messageSender;

    public MessageConsumer(MessageService messageService, MessageSenderService messageSenderService) {
        this.messageService = messageService;
        this.messageSender = messageSenderService;
    }

    @KafkaListener(topics = "${kafka.topics.message_1}", groupId = "${spring.kafka.consumer.group-id}")
    public void sendMessage(MessageSendDTO message) {

        MessageCUDTO messageCUDTO = new MessageCUDTO().setText(message.getText())
                .setToEmail(message.getToEmail())
                .setSubject(message.getSubject());

        MessageEntity messageEntity = messageService.create(messageCUDTO.setStatus(MessageStatus.LOADED));
        Long dateTime = messageEntity.getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond();

        try {
            messageSender.sendMessage(message);
            messageService.update(messageCUDTO.setStatus(MessageStatus.OK), messageEntity.getUuid(), dateTime);
        } catch (MailSendException e) {
            messageService.update(messageCUDTO.setStatus(MessageStatus.ERROR), messageEntity.getUuid(), dateTime);
        }
    }
}