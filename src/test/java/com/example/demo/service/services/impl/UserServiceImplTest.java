package com.example.demo.service.services.impl;

import com.example.demo.config.exceptions.InvalidUserException;
import com.example.demo.data.models.Role;
import com.example.demo.data.models.User;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.service.services.RoleService;
import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserInputModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleService roleService;

    @Autowired
    UserService userService;

    @Test
    void loadUserByUsername_whenNoUserWithThisUsername_shouldReturnNull() {
        Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertNull(userService.loadUserByUsername("username"));
    }

    @Test
    void loadUserByUsername_whenUserExists_shouldReturnUser() {
        User user = new User();
        user.setUsername("ivanivanov");
        Role roleUser = new Role("USER");
        Role roleAdmin = new Role("ADMIN");
        user.setPassword("12345");
        user.setAuthorities(List.of(roleUser, roleAdmin));
        Mockito.when(userRepository.findByUsername("ivanivanov")).thenReturn(Optional.of(user));

        assertEquals("ivanivanov", userService.loadUserByUsername("ivanivanov").getUsername());
        assertEquals(2, userService.loadUserByUsername("ivanivanov").getAuthorities().size());
    }

    @Test
    void register_whenUserWithInvalidData_shouldThrow() {
        UserInputModel userInputModel = new UserInputModel();

        assertThrows(InvalidUserException.class, () -> {
            userService.register(userInputModel);
        });
    }

    @Test
    void register_whenUserWithExistingUsername_shouldThrow() {
//        User user1 = new User();
//        user1.setUsername("ivan.ivanov");
//        userRepository.saveAndFlush(user1);
//        UserInputModel userInputModel = new UserInputModel("1", "ivan.ivanov", "user@user.bg", "+359888123456", "Ivan Ivanov", "12345");
//        Mockito.when(userRepository.saveAndFlush(modelMapper.map(userInputModel, User.class))).thenReturn(modelMapper.map(userInputModel, User.class));
//
//        assertThrows(InvalidUserException.class, () -> {
//            userService.register(userInputModel);
//        });
    }

    @Test
    void register_whenUserValid_shouldReturnUser() throws InvalidUserException {
        User user = new User("username", "parola", "+35912345678", "user@user.bg", "Ivan Ivanov", null, null);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        UserInputModel userInputModel = new UserInputModel("1", "username", "parola", "+35912345678", "user@user.bg", "Ivan Ivanov");

        assertEquals("username", userService.register(userInputModel).getUsername());
    }

    @Test
    void getUserDetails_whenUsernameDoNotExist_shouldReturnNull() {
        assertNull(userService.getUserDetails("pesho"));
    }

    @Test
    void getUserDetails_whenUsernameExists_shouldReturnUserWithThisUsername() {
        User user = new User();
        user.setUsername("testuser");
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        assertEquals("testuser", userService.getUserDetails("testuser").getUsername());
    }

    @Test
    void updateUserDetails_whenUsernameDoNotExist_shouldReturnNull() {
        assertNull(userService.updateUserDetails("pesho", new UserInputModel()));
    }

    @Test
    void updateUserDetails_whenUsernameExists_shouldReturnUserWithNewUsername() {
        User user = new User();
        user.setUsername("testuser");
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User updatedUser = new User();
        updatedUser.setUsername("newtestuser");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(updatedUser);

        UserInputModel userInputModel = new UserInputModel();
        userInputModel.setUsername("newtestuser");

        assertEquals("newtestuser", userService.updateUserDetails("testuser", userInputModel).getUsername());
    }

    @Test
    void getAllUsers_whenNoUsers_shouldReturnEmptyList() {
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());

        assertTrue(userService.getAllUsers().isEmpty());
    }

    @Test
    void getAllUsers_whenTwoUsers_shouldReturnListWithTwoUsers() {
        User user1 = new User();
        Role role = new Role();
        role.setAuthority("USER");
        user1.setAuthorities(List.of(role));
        User user2 = new User();
        user2.setAuthorities(List.of(role));
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        assertEquals(2, userService.getAllUsers().size());
    }
}
