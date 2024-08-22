package com.kozich.finance.user_service.service.impl;

import com.kozich.finance.user_service.service.api.MessageSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
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
    public void sendEmail(String toEmail, String code) throws MailSendException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(toEmail);
        message.setSubject("Код верификации");
        message.setText(code);

        mailSender.send(message);
    }

}
