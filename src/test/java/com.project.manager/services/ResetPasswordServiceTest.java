package com.project.manager.services;

import com.project.manager.repositories.UserRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResetPasswordServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ResetPasswordService resetPasswordService;

//    @Test(expected = EmptyUsernameException.class)
//    public void testExpectedEmptyUsername() throws EmailValidationException{
//        Optional<UserModel> userModel = Optional.of(UserModel.builder()
//                .username("username")
//                .email("username@gmail.com").build());
//        when(userRepository.findByUsernameOrEmail("username", "username@gmail.com")).thenReturn(userModel);
//        resetPasswordService.resetPassword("");
//    }
//
//    @Test(expected = UserDoesNotExistException.class)
//    public void testExpectedUserDoesNotExist() throws EmailValidationException{
//        when(userRepository.findByUsernameOrEmail("usernameasd", "usernameasd")).thenReturn(Optional.empty());
//        resetPasswordService.resetPassword("usernameasd");
//    }



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


//    @Test(expected = DifferentPasswordException.class)
//    public void testResetPasswordExpectDifferentPassword() {
//        resetPasswordService.changePassword("username@mail.com", "PASSWORD", "otherpass");
//    }

}
