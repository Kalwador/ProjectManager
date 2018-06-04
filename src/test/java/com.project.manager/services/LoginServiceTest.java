package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.UserRepository;
import com.project.manager.services.login.LoginService;
import com.project.manager.services.user.UserService;
import com.project.manager.utils.BCryptEncoder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private RememberUserService rememberUserService;

    @Spy
    @InjectMocks
    private LoginService loginService;


//    @Test(expected = DifferentPasswordException.class)
//    public void testExpectDifferentPasswords(){
//        UserModel userModel = getExampleUser().get();
//        when(userRepository.findByUsernameOrEmail(userModel.getUsername(), userModel.getUsername())).thenReturn(Optional.of(userModel));
//        doNothing().when(userService).increaseIncorrectLoginCounter(userModel);
//        loginService.loginUser("username","different", false);
//    }
//
//    @Test(expected = UserDoesNotExistException.class)
//    public void testExpectedUserDoesNotExist() {
//        when(userRepository.findByUsernameOrEmail("usernameasd", "usernameasd")).thenReturn(Optional.empty());
//        loginService.loginUser("usernameasd","123", false);
//    }
//
//    @Test(expected = EmptyPasswordException.class)
//    public void testExpectedEmptyPassword() {
//        UserModel userModel = getExampleUser().get();
//        when(userRepository.findByUsernameOrEmail(userModel.getUsername(), userModel.getUsername())).thenReturn(null);
//        loginService.loginUser("usernameasd","", false);
//    }
//
//    @Test(expected = EmptyUsernameException.class)
//    public void testExpectedEmptyUsername() {
//        UserModel userModel = getExampleUser().get();
//        when(userRepository.findByUsernameOrEmail(userModel.getUsername(), userModel.getUsername())).thenReturn(Optional.of(userModel));
//        loginService.loginUser("","123",false);
//    }
//
//    @Test(expected = AccountBlockedException.class)
//    public void testLoginExpectAccountBlockedException() {
//        UserModel userModel = getExampleUser().get();
//        userModel.setBlocked(true);
//        when(userRepository.findByUsernameOrEmail(userModel.getUsername(), userModel.getUsername())).thenReturn(Optional.of(userModel));
//        loginService.loginUser(userModel.getUsername(), "password", false);
//    }
//
//    @Test(expected = AccountLockedException.class)
//    public void testLoginExpectAccountLockedException() {
//        UserModel userModel = getExampleUser().get();
//        userModel.setLocked(true);
//        when(userRepository.findByUsernameOrEmail(userModel.getUsername(), userModel.getUsername())).thenReturn(Optional.of(userModel));
//        loginService.loginUser(userModel.getUsername(), "password", false);
//    }
//
//    @Test
//    public void testLogin() {
//        Optional<UserModel> userModel = getExampleUser();
//        when(userRepository.findByUsernameOrEmail(userModel.get().getUsername(), userModel.get().getUsername())).thenReturn(userModel);
//        doNothing().when(loginService).loadScene(userModel.get());
//        loginService.loginUser("username","password",false);
//    }
//
//    @Test
//    public void testLoginRememberedUser() {
//        Optional<UserModel> userModel = getExampleUser();
//        when(rememberUserService.getRememberedUser()).thenReturn(userModel);
//        doNothing().when(loginService).loginValidUser(userModel.get());
//        loginService.loginRememberedUser();
//    }

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
