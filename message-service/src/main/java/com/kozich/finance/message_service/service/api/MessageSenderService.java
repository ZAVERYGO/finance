package com.kozich.finance.message_service.service.api;

import com.kozich.finance_storage.core.dto.MessageDTO;
import org.springframework.mail.MailException;

public interface MessageSenderService {

    void sendMessage(MessageDTO messageDTO) throws MailException;

}
