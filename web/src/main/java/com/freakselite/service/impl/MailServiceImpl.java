package com.freakselite.service.impl;

import com.freakselite.model.MailMessage;
import com.freakselite.service.MailService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    // == fields ==
    @Value(value = "${email-username}")
    private String username;
    @Value(value = "${email-password}")
    private String password;
    @Value(value = "${email-recipient}")
    private String to;
    private final Session session;

    // == constructors ==
    @Autowired
    public MailServiceImpl(){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        session = Session.getInstance(prop,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    // == public methods ==
    @Override
    public boolean sendMessage(MailMessage mail) {
        try{

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(to)
            );

            message.setSubject(mail.getSubject());
            message.setText(
                    "Imię:\t" + mail.getName()
                    + "\nEmail:\t" + mail.getEmail()
                    + "\nTreść:\t" + mail.getContent()
            );

            Transport.send(message);

            return true;

        }catch (MessagingException e){

            e.printStackTrace();
            return false;

        }
    }
}
