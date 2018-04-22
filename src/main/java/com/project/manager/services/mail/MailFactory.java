package com.project.manager.services.mail;

import com.project.manager.models.mail.AccountActivationMail;
import com.project.manager.models.mail.Mail;
import com.project.manager.models.mail.ProjectReportMail;
import com.project.manager.models.mail.ResetPasswordMail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Class provides Mailing System, contains methods to sendMail and SendMail with attachment
 * Class contains factory to create mail html content
 */
@Component
public class MailFactory {

    private MailService mailService;
    private Logger logger;

    @Autowired
    public MailFactory(MailService mailService) {
        this.mailService = mailService;
        this.logger = Logger.getLogger(MailFactory.class);
    }

    /**
     * Method send mail without attachment
     *
     * @param mail full prepared object, need to contain recipent and subject
     */
    public void sendMail(Mail mail) {
        String content = "";
        String subject = "";

        switch (mail.getSubject()) {
            case ACCOUNT_ACTIVATION: {
                AccountActivationMail accountActivationMail = (AccountActivationMail) mail;
                content = this.getAccountActivationMessage(accountActivationMail);
                subject = "Task Manager - account activation";
                logger.info("Sending message with account activiation " + mail.toString());
                this.mailService.sendMessage(accountActivationMail.getRecipient(), content, subject);
                break;
            }
            case CUSTOMER_REPORT: {
                ProjectReportMail projectReportMail = (ProjectReportMail) mail;
                content = this.getReportMessage(projectReportMail);
                subject = "Task Manager - Raport";
                logger.info("Sending message with customer report " + mail.toString());
                this.mailService.sendMessageWithAttachment(projectReportMail.getRecipient(), content, subject,
                        projectReportMail.getAttachment());
                break;
            }
            case RESET_PASSWORD: {
                ResetPasswordMail resetPasswordMail = (ResetPasswordMail) mail;
                content = this.getResetPasswordMessage(resetPasswordMail);
                subject = "Task Manager - Password Recovery";
                logger.info("Sending message with reset PASSWORD " + mail.toString());
                this.mailService.sendMessage(resetPasswordMail.getRecipient(), content, subject);
                break;
            }
        }
    }

    /**
     * Create content for activation account in registration process
     *
     * @param mail full prepared object, need to contain username and activation code
     * @return content in string format
     */
    private String getAccountActivationMessage(AccountActivationMail mail) {
        StringBuilder content = new StringBuilder();
        content.append("<h1>Hello " + mail.getUsername() + "</h1>");
        content.append("<p>Thank you for registration in our application, now you can activate your account with code:</p>");
        content.append("<p><b>" + mail.getActivationCode() + "</b></p>");
        content.append("<p>Best regards,</p>");
        content.append("<p>Task Manager - Team</p>");
        logger.info("Creating message ActivationAccount content");
        return content.toString();
    }

    /**
     * Create content for reset code in reset PASSWORD process
     *
     * @param mail full prepared object, need to contain username and activation code and date
     * @return content in string format
     */
    private String getResetPasswordMessage(ResetPasswordMail mail) {
        StringBuilder content = new StringBuilder();
        content.append("<h1>Password Recovery</h1>");
        content.append("<p>You or someone else sent a request to restore the PASSWORD for the " + mail.getUsername() + " account. Use this code to restore PASSWORD</p>");
        content.append("<p><b>" + mail.getActivationCode() + "</b></p>");
        content.append("</br>");
        content.append("<p>Request created on " + LocalDate.now().toString() + "</p>");
        content.append("<p>Best regards,</br>Task Manager - Team</p>");
        logger.info("Creating message ResetPasswordMessage content");
        return content.toString();
    }

    /**
     * Create content for sending report to customer
     *
     * @param mail full prepared object, need to contain customer name, project name, period od time for report
     * @return content in string format
     */
    private String getReportMessage(ProjectReportMail mail) {
        StringBuilder content = new StringBuilder();
        content.append("<h1>Hello " + mail.getRecipientName() + "</h1>");
        content.append("<p>Report of project " + mail.getProjectName() + " </p>");
        content.append("</br>");
        content.append("<p>Report for the period</p>");
        content.append("<p>From: " + mail.getStartDate() + "</p>");
        content.append("<p>To: " + mail.getEndDate() + "</p>");
        content.append("</br>");
        content.append("<p>Best regards,</p>");
        content.append("<p>Task Manager - Team</p>");
        logger.info("Creating message ReportMessage content");
        return content.toString();
    }
}
