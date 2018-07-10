package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.models.UserRole;
import com.project.manager.utils.BCryptEncoder;
import org.junit.runner.RunWith;
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
