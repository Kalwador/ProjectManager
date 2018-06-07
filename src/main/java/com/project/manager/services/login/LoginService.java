package com.project.manager.services.login;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.*;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.UserRepository;
import com.project.manager.services.RememberUserService;
import com.project.manager.services.SessionService;
import com.project.manager.services.user.UserService;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import com.project.manager.utils.BCryptEncoder;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This is the service responsible for login user into our service
 */
@Log4j
@Service
public class LoginService {
    private UserRepository userRepository;
    private SessionService sessionService;
    private RememberUserService rememberUserService;
    private SceneManager sceneManager;
    private UserService userService;


    @Autowired
    public LoginService(UserRepository userRepository, RememberUserService rememberUserService, UserService userService) {
        this.sceneManager = SceneManager.getInstance();
        this.userRepository = userRepository;
        this.rememberUserService = rememberUserService;
        this.sessionService = SessionService.getInstance();
        this.userService = userService;
    }

    /**
     * Method validating propriety of user's PASSWORD and username
     * Additionaly, it invokes SessionService to hold logged user data.
     *
     * @param username user's username given in textfield.
     * @param password user's PASSWORD given in textfield.
     * @param remember stay logged in parameter
     */
    public void loginUser(String username, String password, boolean remember)
            throws UserDoesNotExistException, AccountBlockedException, AccountLockedException,
            EmptyPasswordException, DifferentPasswordException, EmptyUsernameException {
        if (username.isEmpty()) {
            throw new EmptyUsernameException();
        }
        if (password.isEmpty()) {
            throw new EmptyPasswordException();
        }
        Optional<UserModel> usermodel = userRepository.findByUsernameOrEmail(username, username);
        if (!usermodel.isPresent()) {
            throw new UserDoesNotExistException();
        }
        boolean result = BCryptEncoder.check(password, usermodel.get().getPassword());
        if (!result) {
            userService.increaseIncorrectLoginCounter(usermodel.get());
            throw new DifferentPasswordException();
        }
        if (remember) {
            rememberUserService.rememberUser(username);
        }
        loginValidUser(usermodel.get());
    }

    /**
     * Method to valid that user is blocked or not
     *
     * @param usermodel model of user
     */
    private void loginValidUser(UserModel usermodel) throws AccountBlockedException, AccountLockedException {
        if (usermodel.isBlocked()) {
            throw new AccountBlockedException();
        }
        if (usermodel.isLocked()) {
            throw new AccountLockedException();
        } else {
            sessionService.setUserModel(usermodel);
            log.info(usermodel.getUsername() + " has just logged in!");
            loadScene();
        }
    }

    private void loadScene() {
        UserRole role = sessionService.getUserModel().getRole();
        switch (role) {
            case USER:
                sceneManager.showScene(SceneType.DASHBOARD);
                break;
            case ADMIN:
                sceneManager.showScene(SceneType.ADMIN_DASHBOARD);
        }
    }

    /**
     * This method is executing if any user declare to stay logged in
     */
    public void loginRememberedUser() throws AccountBlockedException, AccountLockedException {
        Optional<UserModel> userModel = rememberUserService.getRememberedUser();
        if (userModel.isPresent()) {
            loginValidUser(userModel.get());
        }
    }
}