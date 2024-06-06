package com.kozich.finance.user_service.service.api;

import org.springframework.mail.MailException;

public interface MessageSenderService {

    void sendEmail(String toEmail, String code) throws MailException;

}
