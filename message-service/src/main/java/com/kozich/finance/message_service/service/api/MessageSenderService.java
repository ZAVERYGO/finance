package com.kozich.finance.message_service.service.api;

import com.kozich.finance.message_service.core.dto.MessageSendDTO;
import org.springframework.mail.MailException;

public interface MessageSenderService {

    void sendMessage(MessageSendDTO messageSendDTO) throws MailException;

}
