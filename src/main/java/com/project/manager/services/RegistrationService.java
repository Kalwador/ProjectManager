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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class RegistrationService {

    private UserRepository userRepository;
    private MailFactory mailFactory;

    @Autowired
    public RegistrationService(UserRepository userRepository, MailFactory mailFactory) {
        this.userRepository = userRepository;
        this.mailFactory = mailFactory;
    }

    public void registerUser(String username, String email, String password, String repeatPassword) throws EmailValidationException {
        if (!EmailValidator.isEmailValid(email)) throw new EmailValidationException();

        if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent())
            throw new UserAlreadyExistException("The user with that EMAIL or username already exist in our service");

        if (!password.equals(repeatPassword)) throw new DifferentPasswordException("The passwords aren't the same!");

        String code = ActivationCodeGenerator.generateCode();

        UserModel userModel = UserModel
                .builder()
                .email(email)
                .username(username)
                .password(BCryptEncoder.encode(password))
                .role(UserRole.USER)
                .isBlocked(true)
                .isLocked(false)
                .projectsAsUser(new HashSet<>())
                .projectsAsManager(new HashSet<>())
                .tasks(new HashSet<>())
                .messages(new HashSet<>())
                .activationCode(code)
                .build();
        userRepository.save(userModel);

        sendActivationCodeInMail(userModel);
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