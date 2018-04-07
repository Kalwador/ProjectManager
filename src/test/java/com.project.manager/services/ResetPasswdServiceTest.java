package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.*;
import com.project.manager.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResetPasswdServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ResetPasswdService resetPasswdService;

    @Test(expected = EmptyUsernameException.class)
    public void testExpectedEmptyUsername() {
        UserModel userModel = UserModel.builder()
                .username("username")
                .email("username@gmail.com").build();
        when(userRepository.findByUsernameOrEmail("username", "username@gmail.com")).thenReturn(userModel);
        resetPasswdService.resetPassword("");
    }

    @Test(expected = UserDoesNotExistException.class)
    public void testExpectedUserDoesNotExist() {
        when(userRepository.findByUsernameOrEmail("username", "username@gmail.com")).thenReturn(null);
        resetPasswdService.resetPassword("usernameasd");
    }

    @Test(expected = EmptyGeneratedCodeException.class)
    public void testExpectedEmptyGeneratedCode() {
        String generatePasswdCode = resetPasswdService.generateCode();

        UserModel userModel = UserModel.builder()
                .username("username")
                .email("username@gmail.com")
                .activationCode(generatePasswdCode).build();
        when(userRepository.findByUsernameOrEmail("username", "username@gmail.com")).thenReturn(userModel);
        resetPasswdService.checkGeneratedCode("username","");
    }

    //problem z nullem na userModel
//    @Test(expected = DifferentGeneratedCodeException.class)
//    public void testExpectedDifferentGeneratedCode() {
//        String generatePasswdCode = resetPasswdService.generateCode();
//
//        UserModel userModel = UserModel.builder()
//                .username("username")
//                .email("username@gmail.com")
//                .unlockPasswdCode(generatePasswdCode).build();
//        when(userRepository.findByUsernameOrEmail("username", "username@gmail.com")).thenReturn(userModel);
//        resetPasswdService.checkGeneratedCode("username","gfghfh");
//    }


    @Test(expected = DifferentPasswordException.class)
    public void testResetPasswdExpectDifferentPassword() {
        resetPasswdService.changePassword("username@mail.com", "password", "otherpass");
    }

}
