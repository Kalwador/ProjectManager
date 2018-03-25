package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.EmptyUsernameException;
import com.project.manager.exceptions.UserDoesNotExistException;
import com.project.manager.repositories.UserRepository;
import javafx.scene.control.Alert;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResetPasswdService {

    private UserRepository userRepository;

    public ResetPasswdService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void resetPassword(String usernameOrEmail){

        if (usernameOrEmail.isEmpty()) {
            throw new EmptyUsernameException("Username or Email field can't be empty.");
        }

        UserModel userModel = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (!Optional.ofNullable(userModel).isPresent()) {
            throw new UserDoesNotExistException("There is no user with that username or email in our service.");
        }else{
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Confirmation Dialog");
//            alert.setHeaderText(Optional.ofNullable(userModel.getUsername()).toString());
//            alert.setContentText("Are you sure it is OK?");
//
//            alert.showAndWait();

            userModel.setBlocked(true);
            userRepository.save(userModel);
        }






    }
}
