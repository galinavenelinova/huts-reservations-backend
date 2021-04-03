package com.example.demo.service.services.impl;

import com.example.demo.config.exceptions.InvalidUserException;
import com.example.demo.data.models.Role;
import com.example.demo.data.models.User;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.service.services.RoleService;
import com.example.demo.service.services.UserService;
import com.example.demo.web.models.UserInputModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    RoleService roleService = Mockito.mock(RoleServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserService userService = new UserServiceImpl(userRepository, roleService, modelMapper, passwordEncoder);

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
////        Mockito.when(userRepository.saveAndFlush(modelMapper.map(userInputModel, User.class))).thenReturn(modelMapper.map(userInputModel, User.class));
//
//        assertThrows(InvalidUserException.class, () -> {
//            userService.register(userInputModel);
//        });
    }

    @Test
    void getUserDetails() {
    }

    @Test
    void updateUserDetails() {
    }

    @Test
    void getAllUsers() {
    }
}
