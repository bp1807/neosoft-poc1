package com.neosoft.poc1.service;

import com.neosoft.poc1.exception.UserNotFoundException;
import com.neosoft.poc1.model.User;
import com.neosoft.poc1.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    private UserService userService;

    @BeforeEach
    public void setup() {
        this.userService = new UserService(this.userRepo);
    }

    @Test
    public void addUserSuccessTest() {
        User user = new User();
        user.setName("Bhavik");
        user.setSurname("Parmar");
        user.setPincode("400070");
        user.setDateOfBirth(LocalDate.of(1997,1,1));
        user.setDateOfJoining(LocalDate.of(2021,1,1));
        User createdUser = new User(123, "Bhavik", "Parmar", "400070", LocalDate.of(1997,1,1), LocalDate.of(2021,1,1),false);

        Mockito.when(userRepo.save(user)).thenReturn(createdUser);

        User responseUser = userService.addUser(user);

        assertThat(responseUser).isEqualTo(createdUser);
    }

    @Test
    public void getUserSuccessTest() {
        User actualUser = new User(123, "Bhavik", "Parmar", "400070", LocalDate.of(1997,1,1), LocalDate.of(2021,1,1),false);

        Mockito.when(userRepo.findById(123)).thenReturn(Optional.of(actualUser));

        User responseUser = userService.getUser(123);

        assertThat(responseUser).isEqualTo(actualUser);

    }

    @Test
    public void getUserExceptionTest() {
        Mockito.when(userRepo.findById(123)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUser(123);
        });

        assertTrue(exception.getMessage().contains("User with id 123 does not exist"));
    }

    @Test
    public void editUserSuccessTest() {
        User user = new User();
        user.setName("Yash");
        User originalUser = new User(123, "Bhavik", "Parmar", "400070", LocalDate.of(1997,1,1), LocalDate.of(2021,1,1),false);
        User updatedUser = new User(123, "Yash", "Parmar", "400070", LocalDate.of(1997,1,1), LocalDate.of(2021,1,1),false);

        Mockito.when(userRepo.findById(123)).thenReturn(Optional.of(originalUser));
        Mockito.when(userRepo.save(Mockito.any(User.class))).thenReturn(updatedUser);

        User responseUser = userService.editUser(123, user);

        assertThat(responseUser).isEqualTo(updatedUser);

    }


    @Test
    public void editUserExceptionTest() {
        Mockito.when(userRepo.findById(123)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.editUser(123, new User());
        });

        assertTrue(exception.getMessage().contains("User with id 123 does not exist"));
    }

    @Test
    public void deleteUserSuccessTest() {
        Mockito.when(userRepo.existsById(123)).thenReturn(true);

        Boolean response = userService.deleteUser(123);

        Mockito.verify(userRepo).deleteById(123);

        assertThat(response).isTrue();
    }

    @Test
    public void deleteUserExceptionTest() {
        Mockito.when(userRepo.existsById(123)).thenReturn(false);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(123);
        });

        assertTrue(exception.getMessage().contains("User with id 123 does not exist"));
    }



    @Test
    public void purgeUserSuccessTest() {
        userService.purgeUser(123);

        Mockito.verify(userRepo).purgeUserById(123);
    }

}