package com.project.manager.services;

import com.project.manager.BCryptEncoder;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.DifferentPasswordException;
import com.project.manager.exceptions.EmptyUsernameException;
import com.project.manager.exceptions.EmptyPasswordException;
import com.project.manager.exceptions.UserDoesNotExistException;
import com.project.manager.repositories.UserRepository;

import com.project.manager.services.LoginService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;


    @Test(expected = DifferentPasswordException.class)
    public void testExpectDifferentPasswords(){
        UserModel userModel = UserModel.builder()
                .username("username")
                .email("username@gmail.com")
                .password(BCryptEncoder.encode("password"))
                .isLocked(true).build();
        when(userRepository.findByUsername("username")).thenReturn(userModel);
        loginService.loginUser("username",userModel.getPassword());
    }

    @Test(expected = UserDoesNotExistException.class)
    public void testExpectedUserDoesNotExist() {
        UserModel userModel = UserModel.builder()
                .username("username")
                .email("username@gmail.com")
                .password("")
                .isLocked(true).build();
        when(userRepository.findByUsername("username")).thenReturn(null);
        loginService.loginUser("usernameasd","123");
    }

    @Test(expected = EmptyPasswordException.class)
    public void testExpectedEmptyPassword() {
        UserModel userModel = UserModel.builder()
                .username("username")
                .email("username@gmail.com")
                .password("")
                .isLocked(true).build();
        when(userRepository.findByUsername("username")).thenReturn(null);
        loginService.loginUser("usernameasd","");
    }

    @Test(expected = EmptyUsernameException.class)
    public void testExpectedEmptyUsername() {
        UserModel userModel = UserModel.builder()
                .username("username")
                .email("username@gmail.com")
                .password(BCryptEncoder.encode("123"))
                .isLocked(true).build();
        when(userRepository.findByUsername("username")).thenReturn(userModel);
        loginService.loginUser("","123");
    }

    @Test
    public void testLogin() {
        UserModel userModel = UserModel.builder()
                .username("username")
                .email("username@gmail.com")
                .password(BCryptEncoder.encode("123"))
                .isLocked(true).build();
        when(userRepository.findByUsername("username")).thenReturn(userModel);
        loginService.loginUser("username","123");
    }


}
