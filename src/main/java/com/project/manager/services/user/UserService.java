package com.project.manager.services.user;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.FatalError;
import com.project.manager.models.PersonalData;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.UserRepository;
import com.project.manager.services.SessionService;
import com.project.manager.ui.AlertManager;
import com.project.manager.utils.BCryptEncoder;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class is a service which contains all logic operation on users in database
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private SessionService sessionService;

    /**
     * Constructor of this class contains injected repository of users
     *
     * @param userRepository this class provides necessary methods to manage users in database
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.sessionService = SessionService.getInstance();
    }

    /**
     * This method provides all
     *
     * @return list of all users depending on roles
     */
    public List<UserModel> getAllUsers() {
        return userRepository.getAllByRole(UserRole.USER);
    }

    /**
     * This method provides get users from database by inserted Id in parameter
     *
     * @param id Id of user that we need to get
     * @return user with inserted Id in parameter.
     */
    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * This method perform delete user operation on userRepository by inserted user Id in parameter.
     *
     * @param userId Id of user that we need to delete
     */
    public void delete(Long userId) {
        UserModel user = userRepository.getOne(userId);

        Alert alert = AlertManager.showConfirmationAlert("Delete", "Do you really want to delete " +
                "that user?");
        if (alert.getResult().equals(ButtonType.OK)) {
            userRepository.delete(user);
        }
    }

    /**
     * This method perform delete list of users operation on userRepository by inserted list of user indexes
     * in parameter.
     *
     * @param indexes indexes of users that we need to delete
     */
    public void delete(List<Long> indexes) {
        Alert alert = AlertManager.showConfirmationAlert("Delete", "Do you really want to delete " +
                "this " + indexes.size() + " user(s)?");
        if (alert.getResult().equals(ButtonType.OK)) {
            for (Long id : indexes) {
                userRepository.delete(userRepository.findById(id).get());
            }
        }
    }

    /**
     * This is the method which change the lock status of user which id is passed in parameter
     *
     * @param status this is the parameter which contain actual lock value of user to better decide what kind of lock
     *               will do this method
     * @param userId this is the parameter with user id value to lock or unlock selected user in database
     */
    public void changeLockStatus(boolean status, Long userId) {
        UserModel user = userRepository.getOne(userId);
        user.setLocked(!status);
        userRepository.save(user);
    }

    /**
     * Method to change isBlocked property in {@link UserModel} to status
     *
     * @param status    status to set
     * @param userModel passed user model to modification
     */
    public void changeBlockedStatus(boolean status, UserModel userModel) {
        userModel.setBlocked(status);
        userRepository.save(userModel);
    }

    /**
     * This is the method which change the block status for change password operation
     * of user which id is passed in parameter
     *
     * @param userId Id of user that need to change a password
     */
    public void changePassword(Long userId) {
        UserModel user = userRepository.getOne(userId);
        user.setBlocked(true);
        userRepository.save(user);
        AlertManager.showInformationAlert("Account blocked!", "This account is set as blocked.");
    }

    /**
     * This method is increasing the number of incorrect attempt of login
     *
     * @param userModel user which is actually trying to log in
     */
    public void increaseIncorrectLoginCounter(UserModel userModel) {
        int incorrectLoginCount = userModel.getIncorrectLoginCount();
        if (incorrectLoginCount < 3) {
            userModel.setIncorrectLoginCount(incorrectLoginCount + 1);
            userRepository.save(userModel);
        } else {
            changeBlockedStatus(true, userModel);
            userRepository.save(userModel);
        }
    }

    /**
     * Method allows to change personal data of current logged in user such as First Name, Second Name, Password and avatar.
     *
     * @param personalData model containg set of new personal informations
     */
    public void changePersonalData(PersonalData personalData) {
        try {
            UserModel user = userRepository.findByUsername(sessionService.getUserModel().getUsername())
                    .orElseThrow(() -> new FatalError("Your account is no longer in database. Please contact with administrator."));

            if (!personalData.getFirstName().isEmpty()) {
                user.setFirstName(personalData.getFirstName());
            }
            if (!personalData.getLastName().isEmpty()) {
                user.setLastName(personalData.getLastName());
            }
            if (!personalData.getPassword().isEmpty()) {
                user.setPassword(BCryptEncoder.encode(personalData.getPassword()));
            }
            if (Optional.ofNullable(personalData.getAvatar()).isPresent()) {
                user.setAvatar(personalData.getAvatar());
            }

            sessionService.setUserModel(userRepository.save(user));
        } catch (FatalError err1) {
            this.sessionService.logoutUser();
        }
    }
}