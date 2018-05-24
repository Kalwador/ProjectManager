package com.project.manager.services.user;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.EmptyUsernameException;
import com.project.manager.exceptions.NotEnoughPermissionsException;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.repositories.UserRepository;
import com.project.manager.services.SessionService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Patryk Sadok
 * Class responsible for validating logged in user's permissions
 * and returning chosen user with button.
 * <p>
 * Class performs method, which gets list of all usernames in database.
 * Class performs method, which returns UserModel of chosen user.
 */
@Service
@Log4j
public class UserSelectorService {

    private UserRepository userRepository;
    private SessionService sessionService;
    public static String role = "USER";


    @Autowired
    public UserSelectorService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.sessionService = SessionService.getInstance();
    }

    /**
     * This method returns list of all usernames in database.
     *
     * @return list of usernames
     */
    public List<String> getUserList() throws UserDoesNotExistException {
        Optional<List<String>> optionalList = userRepository.findAllUserNames();
        if (optionalList.isPresent()) {
            return optionalList.get();
        }
        throw new UserDoesNotExistException();
    }

    public List<String> getOnlyUserList() {
        Optional<List<String>> optionalList = userRepository.findAllUserNamesByRole();
        if (optionalList.isPresent()) {
            return optionalList.get();
        }
        throw new RuntimeException("No users in database, critical error");
    }

    /**
     * This method returns model of chosen user.
     *
     * @param username given from text field, parameter for searching in database.
     * @return UserModel of found user
     */
    public UserModel findUser(String username)
            throws UserDoesNotExistException, EmptyUsernameException, NotEnoughPermissionsException {

        if (username.isEmpty()) {
            throw new EmptyUsernameException();
        }
        Optional<UserModel> usermodel = userRepository.findByUsername(username);

        if (!usermodel.isPresent()) {
            throw new UserDoesNotExistException();
        }
        role = sessionService.getUserModel().getRole().toString();
        if (role.equals("USER")) {
            throw new NotEnoughPermissionsException();
        }

        log.info("User object with username : '" + username + "' was returned from database");
        return usermodel.get();
    }
}
