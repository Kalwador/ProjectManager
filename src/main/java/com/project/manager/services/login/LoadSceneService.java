package com.project.manager.services.login;

import com.project.manager.models.UserRole;
import com.project.manager.services.SessionService;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import org.springframework.stereotype.Service;

@Service
public class LoadSceneService {
    SessionService sessionService;
    SceneManager sceneManager;

    public LoadSceneService() {
        this.sceneManager = SceneManager.getInstance();
        this.sessionService = SessionService.getInstance();
    }

    public void loadScene() {
        UserRole role = sessionService.getUserModel().getRole();
        switch (role) {
            case USER:
                sceneManager.showScene(SceneType.DASHBOARD);
                break;
            case ADMIN:
                sceneManager.showScene(SceneType.ADMIN_DASHBOARD);
        }
    }
}
