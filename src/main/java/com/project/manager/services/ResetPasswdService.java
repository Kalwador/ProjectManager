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
import java.util.Random;

/**
 * This is the class which is responsible for operations step by step to perform password resetting.
 * This class perform methods pessetPassword, checkGeneratedCode, chcangePassword and generatedCode
 */
@Service
public class ResetPasswdService {

    private SceneManager sceneManager;
    private UserRepository userRepository;


    @Autowired
    public ResetPasswdService(UserRepository userRepository) {
        sceneManager = SceneManager.getInstance();
        this.userRepository = userRepository;
    }

    /**
     * This method perform starting process of resetting password and perform blocking account and generating code to unlock account.
     * @param usernameOrEmail - name or email of user that want to reset password.
     */
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

            /** -----------------------------todo
             * Sending generated code on email to continue reset password
             */

            AlertManager.showInformationAlert("Resetting password", "You started procedure to reset your" +
                    " password. We sent on your email message with special code to continue reset your password.");

            System.out.println(userModel.getUnlockPasswdCode());
        }
    }
    /**
     * This method checking that inserted code is not empty and if is equal to generated code.
     * @param usernameOrEmail - name or email of user that want to reset password.
     * @param generatedCode - code to unlock blocked account.
     */
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

    /**
     * This method perform checking that passwords are the same and password changing.
     * @param usernameOrEmail - name or email of user that want to reset password.
     * @param password - new password.
     * @param repeatPassword - confirmed new password.
     */
    public void changePassword(String usernameOrEmail, String password, String repeatPassword) {

        UserModel userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (!password.equals(repeatPassword)) throw new DifferentPasswordException("The passwords aren't the same!");

        userModel.setPassword(BCryptEncoder.encode(password));
        userModel.setBlocked(false);
        userRepository.save(userModel);

        AlertManager.showInformationAlert("Password changed", "Your password is changed!");

        sceneManager.showScene(SceneType.LOGIN);
    }

    /**
     * This method perform generating unique code that required to unlock account
     */
    public String generateCode() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }


}
