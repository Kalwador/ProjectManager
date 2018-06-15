package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.DifferentPasswordException;
import com.project.manager.exceptions.EmptyPasswordException;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.UserRepository;
import com.project.manager.services.login.LoginService;
import com.project.manager.services.user.UserService;
import com.project.manager.utils.BCryptEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
    public Optional<UserModel> getExampleUser() {
        return Optional.of(UserModel.builder()
                .username("username")
                .email("username@gmail.com")
                .password(BCryptEncoder.encode("password"))
                .isLocked(false)
                .isBlocked(false)
                .role(UserRole.USER).build());
    }
}
