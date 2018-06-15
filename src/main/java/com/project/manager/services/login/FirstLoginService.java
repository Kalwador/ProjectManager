package com.project.manager.services.login;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.DifferentGeneratedCodeException;
import com.project.manager.exceptions.EmptyGeneratedCodeException;
import com.project.manager.repositories.UserRepository;
import com.project.manager.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirstLoginService {

    private UserRepository userRepository;
    private SessionService sessionService;
    private LoadSceneService loadSceneService;

    @Autowired
    public FirstLoginService(UserRepository userRepository, LoadSceneService loadSceneService) {
        this.userRepository = userRepository;
        this.sessionService = SessionService.getInstance();
        this.loadSceneService = loadSceneService;
    }

    public void checkGeneratedCode(String generatedCode)
            throws DifferentGeneratedCodeException, EmptyGeneratedCodeException {

        if (generatedCode.isEmpty()) {
            throw new EmptyGeneratedCodeException();
        }

        UserModel userModel = sessionService.getUserModel();
        if (!userModel.getActivationCode().equals(generatedCode)) {
            throw new DifferentGeneratedCodeException();
        } else {
            userModel.setActivationCode(null);
            userModel.setFirstLogin(false);
            userRepository.save(userModel);
            loadSceneService.loadScene();
        }
    }
}
