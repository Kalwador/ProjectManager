package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.DifferentPasswordException;
import com.project.manager.exceptions.EmailValidationException;
import com.project.manager.exceptions.user.UserAlreadyExistException;
import com.project.manager.repositories.UserRepository;
import com.project.manager.services.mail.MailFactory;
import com.project.manager.utils.BCryptEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MailFactory mailFactory;

    @InjectMocks
    private RegistrationService registrationService;

//    @Test(expected = EmailValidationException.class)
//    public void testRegistrationExpectInvalidEmail() throws EmailValidationException{
//        RegistrationService service = Mockito.spy(registrationService);
//
//        registrationService.registerUser("", "", "", "");
//    }
//
//    @Test(expected = UserAlreadyExistException.class)
//    public void testRegistrationExpectUserAlreadyExist() throws EmailValidationException
//    {
//        Optional<UserModel> userModel = Optional.of(new UserModel());
//        when(userRepository.findByEmail("")).thenReturn(userModel);
//        when(userRepository.findByUsername("")).thenReturn(userModel);
//
//        registrationService.registerUser("", "usernam@mail.com", "", "");
//    }
//
//    @Test(expected = DifferentPasswordException.class)
//    public void testRegistrationExpectDifferentPassword() throws EmailValidationException {
//        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
//        when(userRepository.findByEmail("username@mail.com")).thenReturn(Optional.empty());
//        registrationService.registerUser("username","username@mail.com", "PASSWORD", "otherpass");
//    }
//
//    @Test
//    public void testRegistration() throws EmailValidationException {
//        UserModel userModel = UserModel.builder()
//                                        .username("username")
//                                        .email("username@mail.com")
//                                        .password(BCryptEncoder.encode("PASSWORD"))
//                                        .isLocked(true).build();
//
//        ArgumentCaptor<UserModel> arg = ArgumentCaptor.forClass(UserModel.class);
//
//        when(userRepository.findByUsername(userModel.getUsername())).thenReturn(Optional.empty());
//        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(Optional.empty());
//        when(userRepository.save(userModel)).thenReturn(arg.capture());
//
//        registrationService.registerUser(userModel.getUsername(), userModel.getEmail(), "PASSWORD", "PASSWORD");
//
//        verify(userRepository, times(1)).findByUsername(userModel.getUsername());
//        verify(userRepository, times(1)).findByEmail(userModel.getEmail());
//        verify(userRepository, times(1)).save(arg.capture());
//        verifyNoMoreInteractions(userRepository);
//    }
}
