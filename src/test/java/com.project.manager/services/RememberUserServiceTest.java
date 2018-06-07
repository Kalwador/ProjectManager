package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RememberUserServiceTest {

    private String fileName = "remembered.data";

    @InjectMocks
    private RememberUserService rememberUserService;

    @Mock
    private UserRepository userRepository;


    @Test
    public void testRememberUser() throws IOException {
        String username = "username";
        rememberUserService.rememberUser(username);

        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        File file = new File(fileName);

        assertTrue(file.exists());
        assertNotEquals(line, username);
    }

    @Test
    public void testDeleteRememberedUser() throws IOException {
        rememberUserService.deleteRememberedUser();
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        File file = new File(fileName);

        assertTrue(line.isEmpty());
        assertTrue(file.exists());
    }

    @Test
    public void testGetRememberedUser() {
        rememberUserService.rememberUser("someusername");
        when(userRepository.findByUsernameOrEmail("someusername","someusername")).thenReturn(getExampleUser());
        Optional<UserModel> userModel = rememberUserService.getRememberedUser();

        assertNotNull(userModel);
        assertEquals(userModel.get().getUsername(), userModel.get().getUsername());
    }

    @Test
    public void testIsAnyRememberedUser() {
        boolean result = rememberUserService.isAnyRememberedUser();

        assertFalse(result);
    }

    public Optional<UserModel> getExampleUser() {
        return new LoginServiceTest().getExampleUser();
    }
}
