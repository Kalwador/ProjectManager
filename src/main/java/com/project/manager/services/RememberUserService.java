package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.util.Optional;

import static com.project.manager.utils.AESAlgorithm.decrypt;
import static com.project.manager.utils.AESAlgorithm.encrypt;

/**
 * This service provides a methods to remember last logged user if he decide it
 */
@Service
public class RememberUserService {

    private final String fileName = "remembered.data";
    private String username;
    private Optional<UserModel> userModel = null;
    private UserRepository userRepository;
    private PrintWriter printWriter;

    @Autowired
    public RememberUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method is creating a file to remember last logged user
     *
     * @param usernameOrEmail username of isEmailValid of user
     * @return
     */
    public boolean rememberUser(String usernameOrEmail) {
        try {
            printWriter = new PrintWriter(fileName, "UTF-8");
            printWriter.println(encrypt(usernameOrEmail));
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This method delete the data in file which is responsible for storing the data about last logged user
     */
    public void deleteRememberedUser() {
        try {
            printWriter = new PrintWriter(fileName, "UTF-8");
            printWriter.println("");
            printWriter.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is reading stored data and deciphering their
     */
    private void readUserData() {
        try {
            File file = new File(fileName);
            String line = null;
            if (file.exists()) {
                FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                    username = decrypt(line);
                }
                bufferedReader.close();
            }
            if (!Optional.ofNullable(username).isPresent() || username.isEmpty()) {
                username = "";
            }
        } catch (IllegalBlockSizeException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method status of remembered user
     *
     * @return true if user is remembered
     */
    public boolean isAnyRememberedUser() {
        return Optional.ofNullable(userModel).isPresent();
    }

    /**
     * This method is providing original user account depending on stored data in remember file
     *
     * @return {@link UserModel }
     */
    public Optional<UserModel> getRememberedUser() {
        if (!Optional.ofNullable(userModel).isPresent()) {
            readUserData();
            if (Optional.ofNullable(username).isPresent() && !username.isEmpty()) {
                this.userModel = userRepository.findByUsernameOrEmail(username, username);
            }
        }
        return userModel;
    }
}
