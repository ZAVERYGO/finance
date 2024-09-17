package com.kozich.finance.message_service.service.impl;

import com.kozich.finance.message_service.core.dto.MessageSendDTO;
import com.kozich.finance.message_service.service.api.MessageSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MessageSenderServiceImpl implements MessageSenderService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String mailFrom;

    public MessageSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMessage(MessageSendDTO messageSendDTO) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(messageSendDTO.getToEmail());
        message.setSubject(messageSendDTO.getSubject());
        message.setText(messageSendDTO.getText());

        mailSender.send(message);
    }
}
