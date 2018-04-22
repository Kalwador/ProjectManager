package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.*;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.models.mail.MailSubject;
import com.project.manager.models.mail.ProjectReportMail;
import com.project.manager.models.mail.ResetPasswordMail;
import com.project.manager.repositories.UserRepository;
import com.project.manager.services.mail.MailFactory;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import com.project.manager.ui.AlertManager;
import com.project.manager.utils.ActivationCodeGenerator;
import com.project.manager.utils.BCryptEncoder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

/**
 * This is the class which is responsible for operations step by step to perform PASSWORD resetting.
 * This class perform methods pessetPassword, checkGeneratedCode, chcangePassword and generatedCode
 */
@Service
public class ResetPasswordService {

    private SceneManager sceneManager;
    private UserRepository userRepository;
    private MailFactory mailFactory;
    private final Logger logger;

    @Autowired
    public ResetPasswordService(UserRepository userRepository, MailFactory mailFactory) {
        this.sceneManager = SceneManager.getInstance();
        this.userRepository = userRepository;
        this.mailFactory = mailFactory;
        this.logger = Logger.getLogger(ResetPasswordService.class);
    }

    /**
     * This method perform starting process of resetting PASSWORD and perform blocking account and generating code to unlock account.
     *
     * @param usernameOrEmail - name or EMAIL of user that want to reset PASSWORD.
     */
    public void resetPassword(String usernameOrEmail) {

        if (usernameOrEmail.isEmpty()) {
            throw new EmptyUsernameException("Username or Email field can't be empty.");
        }

        UserModel userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (!Optional.ofNullable(userModel).isPresent()) {
            throw new UserDoesNotExistException("There is no user with that username or EMAIL in our service.");
        } else {
            String generatePasswordCode = ActivationCodeGenerator.generateCode();

            userModel.setBlocked(true);
            userModel.setActivationCode(generatePasswordCode);
            userRepository.save(userModel);

            AlertManager.showInformationAlert("Resetting PASSWORD", "You started procedure to reset your" +
                    " PASSWORD. We sent on your EMAIL message with special code to continue reset your PASSWORD.");

            sendActivationCodeInMessage(userModel);
        }
    }

    private void sendActivationCodeInMessage(UserModel userModel) {
        ResetPasswordMail mail = ResetPasswordMail.builder()
                .activationCode(userModel.getActivationCode())
                .username(userModel.getUsername())
                .build();
        mail.setRecipient(userModel.getEmail());
        mail.setSubject(MailSubject.RESET_PASSWORD);

        this.mailFactory.sendMail(mail);
    }

    /**
     * This method checking that inserted code is not empty and if is equal to generated code.
     *
     * @param usernameOrEmail - name or EMAIL of user that want to reset PASSWORD.
     * @param generatedCode   - code to unlock blocked account.
     */
    public void checkGeneratedCode(String usernameOrEmail, String generatedCode) {

        UserModel userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (generatedCode.isEmpty()) {
            throw new EmptyGeneratedCodeException("Insert your code please.");
        }
        if (!userModel.getActivationCode().equals(generatedCode)) {
            throw new DifferentGeneratedCodeException("Inserted code is incorrect.");
        } else {
            userModel.setActivationCode(null);
            userRepository.save(userModel);
        }
    }

    /**
     * This method perform checking that passwords are the same and PASSWORD changing.
     *
     * @param usernameOrEmail - name or EMAIL of user that want to reset PASSWORD.
     * @param password        - new PASSWORD.
     * @param repeatPassword  - confirmed new PASSWORD.
     */
    public void changePassword(String usernameOrEmail, String password, String repeatPassword) {

        UserModel userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (!password.equals(repeatPassword)) throw new DifferentPasswordException("The passwords aren't the same!");

        userModel.setPassword(BCryptEncoder.encode(password));
        userModel.setBlocked(false);
        userRepository.save(userModel);

        AlertManager.showInformationAlert("Password changed", "Your PASSWORD is changed!");

        sceneManager.showScene(SceneType.LOGIN);
    }
}
