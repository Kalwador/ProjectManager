package com.project.manager.services.user;

import com.project.manager.entities.UserModel;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.MessageRepository;
import com.project.manager.repositories.ProjectRepository;
import com.project.manager.repositories.UserRepository;
import com.project.manager.ui.AlertManager;
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
    private MessageRepository messageRepository;
    private ProjectRepository projectRepository;

    /**
     * Constructor of this class contains injected repository of users
     * @param userRepository this class provides necessary methods to manage users in database
     */
    @Autowired
    public UserService(UserRepository userRepository,
                       MessageRepository messageRepository,
                       ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * This method provides all
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
    public Optional<UserModel> getUserById(Long id) {return userRepository.findById(id);}

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
                "this "+ indexes.size() +" user(s)?");
        if (alert.getResult().equals(ButtonType.OK)) {
            for (Long id : indexes) {
                userRepository.delete(userRepository.findById(id).get());
            }
        }
    }

    /**
     * This is the method which change the lock status of user which id is passed in parameter
     * @param status this is the parameter which contain actual lock value of user to better decide what kind of lock
     *          will do this method
     * @param userId this is the parameter with user id value to lock or unlock selected user in database
     */
    public void changeLockStatus(boolean status, Long userId) {
        UserModel user = userRepository.getOne(userId);
        user.setLocked(!status);
        userRepository.save(user);
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
     * @param userModel user which is actually trying to log in
     */
    public void increaseIncorrectLoginCounter(UserModel userModel) {
        int incorrectLoginCount = userModel.getIncorrectLoginCount();
        if (incorrectLoginCount < 3) {
            userModel.setIncorrectLoginCount(incorrectLoginCount + 1);
            userRepository.save(userModel);
        }
        else {
            changeBlockedStatus(true, userModel);
            userRepository.save(userModel);
        }
    }

    /**
     * Method to change isBlocked property in {@link UserModel} to status
     * @param status status to set
     * @param userModel passed user model to modification
     */
    public void changeBlockedStatus(boolean status, UserModel userModel) {
        userModel.setBlocked(status);
        userRepository.save(userModel);
    }
}
