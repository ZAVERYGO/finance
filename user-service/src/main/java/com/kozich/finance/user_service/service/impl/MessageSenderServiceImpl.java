package com.kozich.finance.user_service.service.impl;

import com.kozich.finance.user_service.service.api.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MessageSenderServiceImpl implements MessageSenderService {

    private final JavaMailSender mailSender;

    @Autowired
    public MessageSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String toEmail, String code) throws MailSendException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Kozichnick@mail.ru");
        message.setTo(toEmail);
        message.setSubject("Код верификации");
        message.setText(code);

        mailSender.send(message);
    }
}
