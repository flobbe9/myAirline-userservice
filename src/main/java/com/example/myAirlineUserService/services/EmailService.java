package com.example.myAirlineUserService.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotBlank;


@Service
@Validated
public class EmailService {
    
    @Autowired 
    private JavaMailSender javaMailSender;


    /**
     * Sending a standard email using the sender adress specified in application.properties.
     * 
     * @param to target email adress
     * @param content text of the email
     * @param subject of the email
     * @param attachment file attachments, may be null
     * @throws IllegalStateException if MessagingException is caught
     */
    @Async
    public void sendEmail(@NotBlank String to, 
                          @NotBlank String content, 
                          @NotBlank String subject, 
                          File attachment) {
        
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
            
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            if (attachment != null) 
                mimeMessageHelper.addAttachment(attachment.getName(), attachment);

            javaMailSender.send(mailMessage);

        } catch(MessagingException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}