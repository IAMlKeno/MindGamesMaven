package com.portfolio.elkeno_jones.mindgamesmaven.util;

import java.io.File;
import java.nio.file.Path;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class JavaMailer {

    private MailSender mailSender;
    private JavaMailSender jvSender;

    public void setJvSender(JavaMailSender jvSender) {
        this.jvSender = jvSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String from, String to, String subject, String msg) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }

    public void sendMailWithAttachment(String from, String to,
            String subject, String msg, Path pathToAttachment,
            String filename) 
            throws MessagingException {
        MimeMessage message = jvSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(msg);

        helper.addAttachment(filename, pathToAttachment.toFile());

        jvSender.send(message);
    }
}
