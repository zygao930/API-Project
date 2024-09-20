package com.project.project.dao;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.project.project.dao.UserDao;
import com.project.project.entity.User;
import com.project.project.service.EntityService.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService; // Assuming you have a UserService that uses UserDao

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByNickName() {
        // Arrange
        String nickName = "Claire";
        User user = new User();
        user.setNickName(nickName);
        when(userDao.findByNickName(nickName)).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userService.getUserByNickName(nickName);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(nickName, foundUser.get().getNickName());
        System.out.println("User found: " + foundUser.get().getNickName());
    }

}
