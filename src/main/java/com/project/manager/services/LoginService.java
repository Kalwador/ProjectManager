package com.project.manager.services;

import com.project.manager.BCryptEncoder;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.DifferentPasswordException;
import com.project.manager.exceptions.EmptyPasswordException;
import com.project.manager.exceptions.EmptyUsernameException;
import com.project.manager.exceptions.UserDoesNotExistException;
import com.project.manager.repositories.UserRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private UserRepository userRepository;
    private static SessionService sessionService=new SessionService();
    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        sessionService.getData(usermodel);
    }
}
