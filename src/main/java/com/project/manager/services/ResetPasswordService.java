package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.*;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.models.mail.MailSubject;
import com.project.manager.models.mail.ResetPasswordMail;
import com.project.manager.repositories.UserRepository;
import com.project.manager.services.mail.MailFactory;
import com.project.manager.ui.AlertManager;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import com.project.manager.utils.ActivationCodeGenerator;
import com.project.manager.utils.BCryptEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This is the class which is responsible for operations step by step to perform password resetting.
 * This class perform methods pessetPassword, checkGeneratedCode, chcangePassword and generatedCode
 */
@Service
public class ResetPasswordService {

    private SceneManager sceneManager;
    private UserRepository userRepository;
    private MailFactory mailFactory;


    @Autowired
    public ResetPasswordService(UserRepository userRepository, MailFactory mailFactory) {
        this.sceneManager = SceneManager.getInstance();
        this.userRepository = userRepository;
        this.mailFactory = mailFactory;
    }

    /**
     * This method perform starting process of resetting password and perform blocking account and generating code to unlock account.
     *
     * @param usernameOrEmail - name or email of user that want to reset password.
     */
    public void resetPassword(String usernameOrEmail) throws EmailValidationException {

        if (usernameOrEmail.isEmpty()) {
            throw new EmptyUsernameException("Username or Email field can't be empty.");
        }

        Optional<UserModel> userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (!userModel.isPresent()) {
            throw new UserDoesNotExistException("There is no user with that username or isEmailValid in our service.");
        } else {
            String generatePasswordCode = ActivationCodeGenerator.generateCode();

            userModel.get().setBlocked(true);
            userModel.get().setActivationCode(generatePasswordCode);
            userRepository.save(userModel.get());

            AlertManager.showInformationAlert("Resetting PASSWORD", "You started procedure to reset your" +
                    " password. We sent on your EMAIL message with special code to continue reset your password.");

            sendActivationCodeInMessage(userModel.get());
        }
    }

    private void sendActivationCodeInMessage(UserModel userModel) throws EmailValidationException {
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
     * @param usernameOrEmail - name or EMAIL of user that want to reset password.
     * @param generatedCode   - code to unlock blocked account.
     */
    public void checkGeneratedCode(String usernameOrEmail, String generatedCode) {

        Optional<UserModel> userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (generatedCode.isEmpty()) {
            throw new EmptyGeneratedCodeException("Insert your code please.");
        }
        if (!userModel.get().getActivationCode().equals(generatedCode)) {
            throw new DifferentGeneratedCodeException("Inserted code is incorrect.");
        } else {
            userModel.get().setActivationCode(null);
            userRepository.save(userModel.get());
        }
    }

    /**
     * This method perform checking that passwords are the same and password changing.
     *
     * @param usernameOrEmail - name or email of user that want to reset password.
     * @param password        - new password.
     * @param repeatPassword  - confirmed new password.
     */
    public void changePassword(String usernameOrEmail, String password, String repeatPassword) {

        Optional<UserModel> userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (!password.equals(repeatPassword)) throw new DifferentPasswordException("The passwords aren't the same!");

        userModel.get().setPassword(BCryptEncoder.encode(password));
        userModel.get().setBlocked(false);
        userRepository.save(userModel.get());

        AlertManager.showInformationAlert("Password changed", "Your password is changed!");

        sceneManager.showScene(SceneType.LOGIN);
    }
}