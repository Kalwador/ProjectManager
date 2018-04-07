package com.project.manager.services;

import com.project.manager.utils.BCryptEncoder;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.DifferentPasswordException;
import com.project.manager.exceptions.EmailValidationException;
import com.project.manager.exceptions.UserAlreadyExistException;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegistrationService {

    private UserRepository userRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String email, String password, String repeatPassword) {
        if (!isValidEmailAddress(email)) throw new EmailValidationException("Your email is invalid!");

        if (Optional.ofNullable(userRepository.findByUsername(username)).isPresent()
                || Optional.ofNullable(userRepository.findByEmail(email)).isPresent())
            throw new UserAlreadyExistException("The user with that email or username already exist in our service");

        if (!password.equals(repeatPassword)) throw new DifferentPasswordException("The passwords aren't the same!");

        String code = generateCode();

        UserModel userModel = UserModel
            .builder()
            .email(email)
            .username(username)
            .role(UserRole.USER)
            .password(BCryptEncoder.encode(password))
            .isBlocked(false)
            .activationCode(null)
            .isLocked(true)
            .activationCode(code)
            .build();
        userRepository.save(userModel);
    }

    private String generateCode() {
        return String.valueOf(new Date().getTime());
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
