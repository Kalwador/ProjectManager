package com.project.manager.services;

import com.project.manager.entities.UserModel;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.UserRepository;
import com.project.manager.services.user.UserService;
import com.project.manager.utils.BCryptEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void getAllUsersTest() {
        when(userRepository.getAllByRole(UserRole.USER)).thenReturn(getExampleUsersList());

        List<UserModel> users = userService.getAllUsers();

        assertNotNull(users);
        assertNotNull(users.get(0));

        assertEquals(users.get(0).getEmail(), getExampleUsersList().get(0).getEmail());
        assertEquals(users.get(0).getFirstName(), getExampleUsersList().get(0).getFirstName());
        assertEquals(users.get(0).getLastName(), getExampleUsersList().get(0).getLastName());
        assertEquals(users.get(0).getUsername(), getExampleUsersList().get(0).getUsername());
        assertEquals(users.get(0).getId(), getExampleUsersList().get(0).getId());
        assertEquals(users.get(0).getRole(), getExampleUsersList().get(0).getRole());
        assertEquals(users.get(0).isLocked(), getExampleUsersList().get(0).isLocked());
    }

    @Test
    public void changeLockStatusTest() {
        UserModel userModel = getExampleUsersList().get(0);
        when(userRepository.getOne(userModel.getId())).thenReturn(userModel);

        userService.changeLockStatus(userModel.isLocked(), userModel.getId());

        assertNotNull(userModel);

        assertEquals(userModel.isLocked(), !getExampleUsersList().get(0).isLocked());
        assertEquals(userModel.getId(), getExampleUsersList().get(0).getId());
        assertEquals(userModel.getRole(), getExampleUsersList().get(0).getRole());
        assertEquals(userModel.getUsername(), getExampleUsersList().get(0).getUsername());
        assertEquals(userModel.getLastName(), getExampleUsersList().get(0).getLastName());
        assertEquals(userModel.getEmail(), getExampleUsersList().get(0).getEmail());
        assertEquals(userModel.getFirstName(), getExampleUsersList().get(0).getFirstName());
    }

    @Test
    public void testIncreaseIncorrectLoginCounter() {
        UserModel userModel = getExampleUsersList().get(0);

        for (int i = 1; i <= 4; i++) {
            userService.increaseIncorrectLoginCounter(userModel);
            when(userRepository.save(userModel)).thenReturn(userModel);

            if (i <= 3) {
                assertFalse(userModel.isBlocked());
            } else {
                assertTrue(userModel.isBlocked());
            }
        }
    }

    @Test
    public void testChangeBlockedStatus() {
        UserModel userModel = getExampleUsersList().get(0);
        userService.changeBlockedStatus(true, userModel);

        assertTrue(userModel.isBlocked());
    }

    public List<UserModel> getExampleUsersList() {
        return Arrays.asList(
                UserModel
                        .builder()
                        .id(1L)
                        .firstName("Adam")
                        .lastName("Mada")
                        .username("adam")
                        .email("adam@mail.com")
                        .role(UserRole.USER)
                        .password(BCryptEncoder.encode("PASSWORD"))
                        .isLocked(false)
                        .isBlocked(false)
                        .build(),

                UserModel
                        .builder()
                        .id(1L)
                        .firstName("Adam2")
                        .lastName("Mada2")
                        .username("adam2")
                        .email("adam2@mail.com")
                        .role(UserRole.USER)
                        .password(BCryptEncoder.encode("password2"))
                        .isLocked(false)
                        .isBlocked(false)
                        .build());
    }
}
