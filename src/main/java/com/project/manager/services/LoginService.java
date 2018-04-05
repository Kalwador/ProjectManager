package com.project.manager.services;

import com.project.manager.BCryptEncoder;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.DifferentPasswordException;
import com.project.manager.exceptions.EmptyPasswordException;
import com.project.manager.exceptions.EmptyUsernameException;
import com.project.manager.exceptions.UserDoesNotExistException;
import com.project.manager.repositories.UserRepository;
import com.project.manager.sceneManager.SceneManager;
import com.project.manager.sceneManager.SceneType;
import com.project.manager.ui.AlertManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private UserRepository userRepository;
    private SessionService sessionService;
    private SceneManager sceneManager;

    @Autowired
    public LoginService(UserRepository userRepository) {
        sceneManager = SceneManager.getInstance();
        this.userRepository = userRepository;
        this.sessionService = SessionService.getInstance();
    }

    public void loginUser(String username, String password) {
        if (username.isEmpty()) {
            throw new EmptyUsernameException("Username field can't be empty.");
        }
        if (password.isEmpty()) {
            throw new EmptyPasswordException("Password field can't be empty.");
        }

        UserModel usermodel = userRepository.findByUsername(username);

        if (!Optional.ofNullable(userRepository.findByUsername(username)).isPresent()) {
            throw new UserDoesNotExistException("There is no user with that username in our service.");
        }
        boolean result = BCryptEncoder.check(password, usermodel.getPassword());
        if (!result) {
            throw new DifferentPasswordException("Password you entered was incorrect.");
        }

        if (usermodel.isBlocked()) {
            AlertManager.showInformationAlert("Account blocked!", "Your account is blocked. because somebody send request to change a password. Finish password reset procedure or contact with administrator.");
        } else {
            sessionService.setLoggedUser(usermodel);
            sceneManager.showScene(SceneType.DASHBOARD);
        }
    }
}
