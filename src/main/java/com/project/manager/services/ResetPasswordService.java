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
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This is the class which is responsible for operations step by step to perform password resetting.
 * This class perform methods pessetPassword, checkGeneratedCode, chcangePassword and generatedCode
 */
@Log4j
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
    public void resetPassword(String usernameOrEmail)
            throws EmailValidationException, UserDoesNotExistException, EmptyUsernameException {

        if (usernameOrEmail.isEmpty()) {
            throw new EmptyUsernameException();
        }

        Optional<UserModel> userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (!userModel.isPresent()) {
            throw new UserDoesNotExistException();
        } else {
            String generatePasswordCode = ActivationCodeGenerator.generateCode();

            userModel.get().setBlocked(true);
            userModel.get().setActivationCode(generatePasswordCode);
            userRepository.save(userModel.get());

            log.info("The user with username or email : '" + usernameOrEmail + " was blocked and activation code" +
                    "was send to his email");
            AlertManager.showInformationAlert("Resetting password", "You started procedure to reset your" +
                    " password. We sent on your email message with special code to continue reset your password.");

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
    public void checkGeneratedCode(String usernameOrEmail, String generatedCode)
            throws DifferentGeneratedCodeException, EmptyGeneratedCodeException {

        Optional<UserModel> userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (generatedCode.isEmpty()) {
            throw new EmptyGeneratedCodeException();
        }
        if (!userModel.get().getActivationCode().equals(generatedCode)) {
            throw new DifferentGeneratedCodeException();
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
    public void changePassword(String usernameOrEmail, String password, String repeatPassword)
            throws DifferentPasswordException {

        Optional<UserModel> userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (!password.equals(repeatPassword)) throw new DifferentPasswordException();

        userModel.get().setPassword(BCryptEncoder.encode(password));
        userModel.get().setBlocked(false);
        userRepository.save(userModel.get());

        AlertManager.showInformationAlert("Password changed", "Your password is changed!");

        sceneManager.showScene(SceneType.LOGIN);
    }
}