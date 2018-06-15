package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.DifferentPasswordException;
import com.project.manager.exceptions.EmailValidationException;
import com.project.manager.exceptions.user.UserAlreadyExistException;
import com.project.manager.models.UserRole;
import com.project.manager.models.mail.AccountActivationMail;
import com.project.manager.models.mail.MailSubject;
import com.project.manager.repositories.UserRepository;
import com.project.manager.services.mail.MailFactory;
import com.project.manager.utils.ActivationCodeGenerator;
import com.project.manager.utils.BCryptEncoder;
import com.project.manager.utils.EmailValidator;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Log4j
public class RegistrationService {

    private UserRepository userRepository;
    private MailFactory mailFactory;

    @Autowired
    public RegistrationService(UserRepository userRepository, MailFactory mailFactory) {
        this.userRepository = userRepository;
        this.mailFactory = mailFactory;
    }

    public void registerUser(String username, String email, String password, String repeatPassword)
            throws EmailValidationException, UserAlreadyExistException, DifferentPasswordException {

        if (!EmailValidator.isEmailValid(email)) throw new EmailValidationException();
        if (username.length() < 4 || username.length() > 25)
            throw new IllegalArgumentException("The username must be longer than 4 and shorter than 25 letters");
        if (password.length() < 8 || password.length() > 25)
            throw new IllegalArgumentException("The password must be longer than 8 and shorter than 25 letters");
        if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent())
            throw new UserAlreadyExistException();
        if (!password.equals(repeatPassword)) throw new DifferentPasswordException();

        String code = ActivationCodeGenerator.generateCode();

        UserModel userModel = UserModel
                .builder()
                .email(email)
                .username(username)
                .password(BCryptEncoder.encode(password))
                .role(UserRole.USER)
                .isBlocked(false)
                .isFirstLogin(true)
                .isLocked(false)
                .projectsAsUser(new HashSet<>())
                .projectsAsManager(new HashSet<>())
                .tasks(new HashSet<>())
                .messages(new HashSet<>())
                .activationCode(code)
                .build();
        userRepository.save(userModel);
        sendActivationCodeInMail(userModel);
        log.info("The user : '" + username + "' was successful registered in application!");
    }

    private void sendActivationCodeInMail(UserModel userModel) throws EmailValidationException {
        AccountActivationMail mail = AccountActivationMail.builder()
                .activationCode(userModel.getActivationCode())
                .username(userModel.getUsername())
                .build();
        mail.setRecipient(userModel.getEmail());
        mail.setSubject(MailSubject.ACCOUNT_ACTIVATION);

        mailFactory.sendMail(mail);
    }
}
