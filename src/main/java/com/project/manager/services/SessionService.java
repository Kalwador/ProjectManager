package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.models.UserRole;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private static SessionService instance = null;
    private UserModel userModel;

    public static SessionService getInstance(){
        if(instance == null){
            instance = new SessionService();
        }
        return instance;
    }

    public void setLoggedUser(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getEmail() {
        return userModel.getEmail();
    }

    public String getUsername() {
        return userModel.getUsername();
    }

    public UserRole getRole() {
        return userModel.getRole();
    }

    public long getID() {
        return userModel.getId();
    }
}
