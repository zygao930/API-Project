package com.project.project.dao;
import com.project.project.entity.User;
import com.project.project.util.IdGenerator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    @Disabled
    public void testSaveAndFind() {
        // Arrange
        User user = new User();
        user.setId(IdGenerator.generateId("User"));

        user.setUserId("B3");
        user.setEmail("test2@example.com");
        user.setPassword("securePassword123");
        user.setNickName("TestUser");
        user.setGender("Male");
        user.setAvatarUrl("avatar_url.jpg");
        user.setBalance(new BigDecimal("100.00"));
        user.setPaykey("PayKey123");
        user.setCreateTime(LocalDate.now());
        user.setUpdateTime(LocalDate.now());

        // Act
        User savedUser = userDao.save(user);
        User foundUser = userDao.findById(savedUser.getId()).orElse(null);

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserId()).isEqualTo("B3");
        assertThat(foundUser.getEmail()).isEqualTo("test2@example.com");
        assertThat(foundUser.getPassword()).isEqualTo("securePassword123");
        assertThat(foundUser.getNickName()).isEqualTo("TestUser");
        assertThat(foundUser.getGender()).isEqualTo("Male");
        assertThat(foundUser.getAvatarUrl()).isEqualTo("avatar_url.jpg");
        assertThat(foundUser.getBalance()).isEqualByComparingTo(new BigDecimal("100.00"));
        assertThat(foundUser.getPaykey()).isEqualTo("PayKey123");
        assertThat(foundUser.getCreateTime()).isEqualTo(LocalDate.now());
        assertThat(foundUser.getUpdateTime()).isEqualTo(LocalDate.now());
    }
}
