package com.kozich.finance.user_service.service.job;

import com.kozich.finance.user_service.core.MessageStatus;
import com.kozich.finance.user_service.core.dto.MessageDTO;
import com.kozich.finance.user_service.model.MessageEntity;
import com.kozich.finance.user_service.service.api.MessageSenderService;
import com.kozich.finance.user_service.service.api.MessageService;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageSendJob {

    private final MessageSenderService messageSender;
    private final MessageService messageService;

    public MessageSendJob(MessageSenderService messageSenderService, MessageService messageService) {
        this.messageSender = messageSenderService;
        this.messageService = messageService;
    }

    public void start(){

        List<MessageEntity> allByStatus = messageService.getAllByStatus(MessageStatus.LOADED);

        if(allByStatus == null){
            return;
        }
        for (MessageEntity mail : allByStatus) {

            try {
                messageSender.sendEmail(mail.getUserUuid().getEmail(), mail.getCode());
                MessageDTO messageDTO = new MessageDTO()
                        .setStatus(MessageStatus.OK);
                messageService.update(messageDTO);
            }catch (MailException e){
                MessageDTO messageDTO = new MessageDTO()
                        .setStatus(MessageStatus.ERROR);
                messageService.update(messageDTO);
            }
        }
    }
}
