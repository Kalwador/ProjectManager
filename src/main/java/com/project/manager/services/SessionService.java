package com.project.manager.services;

import com.project.manager.entities.Project;
import com.project.manager.entities.UserModel;
import com.project.manager.models.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionService {

    /**
     * TODO
     */
    private static SessionService instance = null;

    /**
     * User that is actual logged in.
     */
    private UserModel userModel;

    /**
     * Project that user actual working on.
     */
    private Project project;

    private SessionService() {
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public static SessionService getInstance() {
        if (instance == null) {
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
