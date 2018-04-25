package com.project.manager.services.mail;

import com.project.manager.config.MailingSystemConfiguration;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Class provides actions to send diffrent types of messages
 * Class contains methods building object of type Message from javax library
 * Works under MailFactory interface
 */
@Log4j
@Service
class MailServiceImpl implements MailService {

    /**
     * Message without attachment
     *
     * @param recipient EMAIL address of recipient
     * @param content   message content
     * @param subject   message subject
     */
    @Override
    public void sendMessage(String recipient, String content, String subject) {
        Properties prop = getPropertiesOfMailingSystem();

        Session session = Session.getDefaultInstance(prop);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(MailingSystemConfiguration.EMAIL));
            InternetAddress toAddress = new InternetAddress(recipient);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");
            send(message, session);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("messagign exception message = " + recipient + content + subject);
        }
    }

    /**
     * Message with attachment
     *
     * @param recipient EMAIL address of recipient
     * @param content   message content
     * @param subject   message subject
     * @param file      file tat will be sent as attachment
     */
    @Override
    public void sendMessageWithAttachment(String recipient, String content, String subject, File file) {
        Properties properties = getPropertiesOfMailingSystem();
        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MailingSystemConfiguration.EMAIL));
            InternetAddress toAddress = new InternetAddress(recipient);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html");

            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.attachFile(file);

            multipart.addBodyPart(attachPart);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            send(message, session);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            log.error("messagign exception message = " + recipient + content + subject + file.toString());
        }
    }

    /**
     * Send Preparded message using serwer and configuration from MailingSystemConfiguration
     *
     * @param msng    sending message
     * @param session default object from javax mailing library
     */
    private void send(MimeMessage msng, Session session) {
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(MailingSystemConfiguration.HOST,
                    MailingSystemConfiguration.EMAIL,
                    MailingSystemConfiguration.PASSWORD);
            transport.sendMessage(msng, msng.getAllRecipients());
            transport.close();
            log.info("Message sent succesfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("Message could not be sent " + e.getMessage());
        }
    }

    /**
     * @return Properties object containing all data bout smtp server, ready to use
     */
    private Properties getPropertiesOfMailingSystem() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", MailingSystemConfiguration.START_TLS);
        properties.put("mail.smtp.HOST", MailingSystemConfiguration.HOST);
        properties.put("mail.smtp.ssl.trust", MailingSystemConfiguration.SSL_TRUST);
        properties.put("mail.smtp.user", MailingSystemConfiguration.EMAIL);
        properties.put("mail.smtp.PASSWORD", MailingSystemConfiguration.PASSWORD);
        properties.put("mail.smtp.port", MailingSystemConfiguration.PORT);
        properties.put("mail.smtp.auth", MailingSystemConfiguration.AUTH);
        log.info("Message properties set successful");
        return properties;
    }
}