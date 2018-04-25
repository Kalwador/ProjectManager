package com.project.manager.services;


import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.EmptyUsernameException;
import com.project.manager.exceptions.NotEnoughPermissionsException;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.UserRepository;
import com.project.manager.services.login.LoginService;
import com.project.manager.services.user.UserSelectorService;
import com.project.manager.utils.BCryptEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserSelectorServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserSelectorService userSelectorService;
    @Spy
    @InjectMocks
    private LoginService loginService;
    @InjectMocks
    private RegistrationService registrationService;

    @Test(expected = UserDoesNotExistException.class)
    public void testExpectedUserDoesNotExist() {
        when(userRepository.findByUsername("usernameasd")).thenReturn(Optional.empty());
        userSelectorService.findUser("usernameasd");
    }

    @Test(expected = EmptyUsernameException.class)
    public void testExpectedEmptyUsername() {
        userSelectorService.findUser("");
    }

    @Test(expected = NotEnoughPermissionsException.class)
    public void testExpectedInvalidPermission() {
        Optional<UserModel> userModel = Optional.of(UserModel.builder()
                .username("user")
                .email("username@gmail.com")
                .role(UserRole.USER)
                .password(BCryptEncoder.encode("password"))
                .isLocked(false).build());
        when(userRepository.findByUsername("user")).thenReturn(userModel);
        when(userRepository.findByUsernameOrEmail("user", "user")).thenReturn(userModel);
        doNothing().when(loginService).loadScene(userModel.get());
        loginService.loginUser("user","password", false);
        userSelectorService.findUser("user");
    }

    @Test
    public void testAddingUserAsAdmin() {
        Optional<UserModel> userModel = Optional.of(UserModel.builder()
                .username("user")
                .email("username@gmail.com")
                .role(UserRole.ADMIN)
                .password(BCryptEncoder.encode("password"))
                .isLocked(false).build());
        when(userRepository.findByUsername("user")).thenReturn(userModel);
        when(userRepository.findByUsernameOrEmail("user", "user")).thenReturn(userModel);
        doNothing().when(loginService).loadScene(userModel.get());
        loginService.loginUser("user","password", false);
        userSelectorService.findUser("user");
    }
    @Test
    public void testAddingUserAsClient() {
        Optional<UserModel> userModel = Optional.of(UserModel.builder()
                .username("user")
                .email("username@gmail.com")
                .role(UserRole.ADMIN)
                .password(BCryptEncoder.encode("password"))
                .isLocked(false).build());
        when(userRepository.findByUsername("user")).thenReturn(userModel);
        when(userRepository.findByUsernameOrEmail("user", "user")).thenReturn(userModel);
        doNothing().when(loginService).loadScene(userModel.get());
        loginService.loginUser("user","password", false);
        userSelectorService.findUser("user");
    }
}
