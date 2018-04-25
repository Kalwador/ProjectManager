package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.*;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.repositories.UserRepository;
import com.project.manager.utils.ActivationCodeGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResetPasswordServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ResetPasswordService resetPasswordService;

    @Test(expected = EmptyUsernameException.class)
    public void testExpectedEmptyUsername() throws EmailValidationException{
        Optional<UserModel> userModel = Optional.of(UserModel.builder()
                .username("username")
                .email("username@gmail.com").build());
        when(userRepository.findByUsernameOrEmail("username", "username@gmail.com")).thenReturn(userModel);
        resetPasswordService.resetPassword("");
    }

    @Test(expected = UserDoesNotExistException.class)
    public void testExpectedUserDoesNotExist() throws EmailValidationException{
        when(userRepository.findByUsernameOrEmail("usernameasd", "usernameasd")).thenReturn(Optional.empty());
        resetPasswordService.resetPassword("usernameasd");
    }

    @Test(expected = EmptyGeneratedCodeException.class)
    public void testExpectedEmptyGeneratedCode() {
        String generatePasswordCode = ActivationCodeGenerator.generateCode();

        Optional<UserModel> userModel = Optional.of(UserModel.builder()
                .username("username")
                .email("username@gmail.com")
                .activationCode(generatePasswordCode).build());
        when(userRepository.findByUsernameOrEmail("username", "username@gmail.com")).thenReturn(userModel);
        resetPasswordService.checkGeneratedCode("username","");
    }

    //problem z nullem na userModel
//    @Test(expected = DifferentGeneratedCodeException.class)
//    public void testExpectedDifferentGeneratedCode() {
//        String generatePasswordCode = resetPasswordService.generateCode();
//
//        UserModel userModel = UserModel.builder()
//                .username("username")
//                .EMAIL("username@gmail.com")
//                .unlockPasswordCode(generatePasswordCode).build();
//        when(userRepository.findByUsernameOrEmail("username", "username@gmail.com")).thenReturn(userModel);
//        resetPasswordService.checkGeneratedCode("username","gfghfh");
//    }


    @Test(expected = DifferentPasswordException.class)
    public void testResetPasswordExpectDifferentPassword() {
        resetPasswordService.changePassword("username@mail.com", "PASSWORD", "otherpass");
    }

}
