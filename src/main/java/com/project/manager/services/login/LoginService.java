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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This is the service responsible for login user into our service
 */
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
    public void loginUser(String username, String password, boolean remember) {
        if (username.isEmpty()) {
            throw new EmptyUsernameException("Username field can't be empty.");
        }
        if (password.isEmpty()) {
            throw new EmptyPasswordException("Password field can't be empty.");
        }
        Optional<UserModel> usermodel = userRepository.findByUsernameOrEmail(username, username);
        if (!usermodel.isPresent()) {
            throw new UserDoesNotExistException("There is no user with that username in our service.");
        }
        boolean result = BCryptEncoder.check(password, usermodel.get().getPassword());
        if (!result) {
            userService.increaseIncorrectLoginCounter(usermodel.get());
            throw new DifferentPasswordException("Password you entered was incorrect.");
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
    public void loginValidUser(UserModel usermodel) {
        if (usermodel.isBlocked()) {
            throw new AccountBlockedException("Your account is blocked, you should generate code by click forget password to unblock account!");
        }
        if (usermodel.isLocked()) {
            throw new AccountLockedException("Your account is locked, you should contact with administrator of service!");
        } else {
            sessionService.setUserModel(usermodel);
            loadScene(usermodel);
        }
    }

    public void loadScene(UserModel usermodel) {
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
    public void loginRememberedUser() {
        Optional<UserModel> userModel = rememberUserService.getRememberedUser();
        userModel.ifPresent(this::loginValidUser);
    }
}