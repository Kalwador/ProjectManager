package com.project.manager.services.login;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.login.AccountBlockedException;
import com.project.manager.exceptions.login.AccountLockedException;
import com.project.manager.exceptions.login.WrongPasswordException;
import com.project.manager.exceptions.registration.EmptyPasswordException;
import com.project.manager.exceptions.registration.EmptyUsernameException;
import com.project.manager.exceptions.user.UserDoesNotExistException;
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
    private LoadSceneService loadSceneService;

    @Autowired
    public LoginService(UserRepository userRepository, RememberUserService rememberUserService,
                        UserService userService, LoadSceneService loadSceneService) {
        this.sceneManager = SceneManager.getInstance();
        this.sessionService = SessionService.getInstance();
        this.userRepository = userRepository;
        this.rememberUserService = rememberUserService;
        this.userService = userService;
        this.loadSceneService = loadSceneService;
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
            EmptyPasswordException, WrongPasswordException, EmptyUsernameException {
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
            throw new WrongPasswordException();
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
        }
        if (usermodel.isFirstLogin()) {
            sessionService.setUserModel(usermodel);
            sceneManager.showScene(SceneType.FIRST_LOGIN);
        } else {
            sessionService.setUserModel(usermodel);
            log.info(usermodel.getUsername() + " has just logged in!");
            loadSceneService.loadScene();
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