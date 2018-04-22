package com.project.manager.services;

import com.project.manager.utils.BCryptEncoder;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.DifferentPasswordException;
import com.project.manager.exceptions.EmptyPasswordException;
import com.project.manager.exceptions.EmptyUsernameException;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.UserRepository;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import com.project.manager.ui.AlertManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private UserRepository userRepository;
    private SessionService sessionService;
    private SceneManager sceneManager;
    private final Logger logger;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.sceneManager = SceneManager.getInstance();
        this.userRepository = userRepository;
        this.sessionService = SessionService.getInstance();
        this.logger = Logger.getLogger(LoginService.class);
    }

    /**
     * Method validating propriety of user's PASSWORD and username
     * Additionaly, it invokes SessionService to hold logged user data.
     *
     * @param username user's username given in textfield.
     * @param password user's PASSWORD given in textfield.
     */
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
            AlertManager.showInformationAlert("Account blocked!", "Your account is blocked.");
        } else {
            sessionService.setLoggedUser(usermodel);

            UserRole role = sessionService.getRole();

            switch (role) {
                case USER:
                    sceneManager.showScene(SceneType.DASHBOARD);
                    break;
                case ADMIN:
                    sceneManager.showScene(SceneType.ADMIN_DASHBOARD);
            }
        }
    }
}
