package com.project.manager.services;

import com.project.manager.models.mail.AccountActivationMail;
import com.project.manager.models.mail.MailSubject;
import com.project.manager.services.mail.MailFactory;
import com.project.manager.utils.ActivationCodeGenerator;
import com.project.manager.utils.BCryptEncoder;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.DifferentPasswordException;
import com.project.manager.exceptions.EmailValidationException;
import com.project.manager.exceptions.user.UserAlreadyExistException;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegistrationService {

    private UserRepository userRepository;
    private MailFactory mailFactory;
    private final Logger logger;

    @Autowired
    public RegistrationService(UserRepository userRepository, MailFactory mailFactory) {
        this.userRepository = userRepository;
        this.mailFactory = mailFactory;
        this.logger = Logger.getLogger(RegistrationService.class);
    }

    public void registerUser(String username, String email, String password, String repeatPassword) {
        if (!isValidEmailAddress(email)) throw new EmailValidationException("Your EMAIL is invalid!");

        if (Optional.ofNullable(userRepository.findByUsername(username)).isPresent()
                || Optional.ofNullable(userRepository.findByEmail(email)).isPresent())
            throw new UserAlreadyExistException("The user with that EMAIL or username already exist in our service");

        if (!password.equals(repeatPassword)) throw new DifferentPasswordException("The passwords aren't the same!");

        String code = ActivationCodeGenerator.generateCode();

        UserModel userModel = UserModel
                .builder()
                .email(email)
                .username(username)
                .password(BCryptEncoder.encode(password))
                .role(UserRole.USER)
                .isBlocked(false)
                .isLocked(true)
                .projectsAsUser(new HashSet<>())
                .projectsAsManager(new HashSet<>())
                .tasks(new HashSet<>())
                .messages(new HashSet<>())
                .activationCode(code)
                .build();
        userRepository.save(userModel);

        sendActivationCodeInMail(userModel);
    }

    private void sendActivationCodeInMail(UserModel userModel) {
        AccountActivationMail mail = AccountActivationMail.builder()
                .activationCode(userModel.getActivationCode())
                .username(userModel.getUsername())
                .build();
        mail.setRecipient(userModel.getEmail());
        mail.setSubject(MailSubject.ACCOUNT_ACTIVATION);

        mailFactory.sendMail(mail);
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
