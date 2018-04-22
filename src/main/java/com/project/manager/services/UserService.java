package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is a service which contains all logic operation on users in database
 */
@Service
public class UserService {

    private UserRepository userRepository;

    /**
     * Constructor of this class contains injected repository of users
     * @param userRepository this class provides necessary methods to manage users in database
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method provides all
     * @return
     */
    public List<UserModel> getAllUsers() {
        return userRepository.getAllByRole(UserRole.USER);
    }

    /**
     * TO IMPLEMENT!!
     * @param id
     */
    public void delete(long id) {
        System.out.println(id);
    }

    /**
     * This is the method which change the lock status of user which id is passed in parameter
     * @param b this is the parameter which contain actual lock value of user to better decide what kind of lock
     *          will do this method
     * @param userId this is the parameter with user id value to lock or unlock selected user in database
     */
    public void changeLockStatus(boolean b, Long userId) {
        UserModel user = userRepository.getOne(userId);
        user.setLocked(!b);
        userRepository.save(user);
    }

    /**
     * TODO ???
     * TO IMPLEMENT!!
     * @param id
     */
    public void changePassword(long id) {
        System.out.println(id);
    }
}
