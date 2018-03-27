package com.project.manager.services;

import com.project.manager.BCryptEncoder;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.*;
import com.project.manager.repositories.UserRepository;
import com.project.manager.sceneManager.SceneManager;
import com.project.manager.sceneManager.SceneType;
import com.project.manager.ui.AlertManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResetPasswdService {

    private SceneManager sceneManager;
    private UserRepository userRepository;

    @Autowired
    public ResetPasswdService(UserRepository userRepository) {
        sceneManager = SceneManager.getInstance();
        this.userRepository = userRepository;
    }

    public void resetPassword(String usernameOrEmail){

        if (usernameOrEmail.isEmpty()) {
            throw new EmptyUsernameException("Username or Email field can't be empty.");
        }

        UserModel userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (!Optional.ofNullable(userModel).isPresent()) {
            throw new UserDoesNotExistException("There is no user with that username or email in our service.");
        } else {
            String generatePasswdCode = generateCode();

            userModel.setBlocked(true);
            userModel.setUnlockPasswdCode(generatePasswdCode);
            userRepository.save(userModel);

            AlertManager.showInformationAlert("Resetting password", "You started procedure to reset your" +
                    " password. We sent on your email message with special code to continue reset your password.");

            System.out.println(userModel.getUnlockPasswdCode());
        }
    }

    public void checkGeneratedCode(String usernameOrEmail, String generatedCode){

        UserModel userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (generatedCode.isEmpty()){
            throw new EmptyGeneratedCodeException("Insert your code please.");
        }
        if (!userModel.getUnlockPasswdCode().equals(generatedCode)) {
            throw new DifferentGeneratedCodeException("Inserted code is incorrect.");
        } else {
            userModel.setUnlockPasswdCode(null);
            userRepository.save(userModel);
        }
    }

    public void changePassword(String usernameOrEmail, String password, String repeatPassword) {

        UserModel userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (!password.equals(repeatPassword)) throw new DifferentPasswordException("The passwords aren't the same!");

        userModel.setPassword(BCryptEncoder.encode(password));
        userModel.setBlocked(false);
        userRepository.save(userModel);

        AlertManager.showInformationAlert("Password changed", "Your password is changed!");

        sceneManager.showScene(SceneType.LOGIN);
    }

    private String generateCode() {
        return String.valueOf(System.currentTimeMillis());
    }
}
