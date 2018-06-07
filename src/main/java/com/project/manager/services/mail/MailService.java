package com.project.manager.services.mail;

import java.io.File;

/**
 * Interface for sending methods
 */
interface MailService {
    /**
     * Message without attachment
     *
     * @param recipient EMAIL address of recipient
     * @param content   message content
     * @param subject   message subject
     */
    public void sendMessage(String recipient, String content, String subject);

    /**
     * Message with attachment
     *
     * @param recipient EMAIL address of recipient
     * @param content   message content
     * @param subject   message subject
     * @param file      file tat will be sent as attachment
     */
    public void sendMessageWithAttachment(String recipient, String content, String subject, File file);
}
