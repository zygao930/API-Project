package com.project.project.service.EntityService;

import com.project.project.dao.UserDao;
import com.project.project.entity.User;
import com.project.project.controller.VerificationController;
import com.project.project.exception.CommonException;
import com.project.project.util.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.project.util.IdGenerator.generateId;

/**
 * Service class for managing user-related operations such as registration, updating,
 * deleting, and retrieving users.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private VerificationController verificationService;

    /**
     * Registers a new user after verifying the email code.
     *
     * @param registrationDTO Data Transfer Object containing user registration information.
     * @param inputCode The verification code provided by the user.
     * @return A success message indicating that the user has been registered.
     * @throws CommonException If the email is already in use.
     */
    public String registerNewUser(UserRegistrationDTO registrationDTO, String inputCode) throws CommonException {
        System.out.println("开始注册新用户: " + registrationDTO.getEmail());

        // Step 1: Verify the email and the input code
        boolean isValid = verificationService.verifyCode(registrationDTO.getEmail(), inputCode).hasBody();

        if (!isValid) {
            return "验证码无效";
        }

        // Step 2: Check if the email is already used
        if (userDao.existsByEmail(registrationDTO.getEmail())) {
            throw new CommonException(100, "邮箱已使用");
        }

        // Create new User entity
        User user = new User();
        user.setPassword(registrationDTO.getPassWord());
        user.setEmail(registrationDTO.getEmail());
        user.setPaykey(registrationDTO.getPayKey());
        user.setGender(registrationDTO.getGender());
        user.setNickName(registrationDTO.getNickName());
        user.setBalance(registrationDTO.getBalance());
        user.setAvatarUrl(registrationDTO.getAvatarUrl());

        user.setId(generateId("User"));
        user.setUserId(UUID.randomUUID().toString());
        user.setCreateTime(LocalDate.now());
        user.setUpdateTime(LocalDate.now());

        userDao.save(user);
        return "用户注册成功！";
    }

    /**
     * Adds a new user to the system.
     *
     * @param registrationDTO Data Transfer Object containing user registration information.
     * @return A success message indicating that the user was added successfully.
     * @throws CommonException If the email is already in use.
     */
    public String addUser(UserRegistrationDTO registrationDTO) throws CommonException {
        System.out.println("Adding new user: " + registrationDTO.getEmail());

        // Check if the email is already used
        if (userDao.existsByEmail(registrationDTO.getEmail())) {
            throw new CommonException(100, "Email already in use");
        }

        // Create new User entity
        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setNickName(registrationDTO.getNickName());
        user.setPassword(registrationDTO.getPassWord());
        user.setGender(registrationDTO.getGender());
        user.setPaykey(registrationDTO.getPayKey());
        user.setBalance(registrationDTO.getBalance());
        user.setAvatarUrl(registrationDTO.getAvatarUrl());
        user.setId(generateId("User"));
        user.setUserId(UUID.randomUUID().toString());
        user.setCreateTime(LocalDate.now());
        user.setUpdateTime(LocalDate.now());

        userDao.save(user);
        return "User added successfully!";
    }

    /**
     * Updates an existing user in the system.
     *
     * @param id The ID of the user to be updated.
     * @param registrationDTO Data Transfer Object containing updated user information.
     * @return A success message indicating that the user was updated successfully.
     * @throws CommonException If the user is not found.
     */
    public String updateUser(String id, UserRegistrationDTO registrationDTO) throws CommonException {
        User user = userDao.findById(id)
                .orElseThrow(() -> new CommonException(404, "User not found"));

        user.setEmail(registrationDTO.getEmail());
        user.setNickName(registrationDTO.getNickName());
        user.setPassword(registrationDTO.getPassWord());
        user.setGender(registrationDTO.getGender());
        user.setPaykey(registrationDTO.getPayKey());
        user.setBalance(registrationDTO.getBalance());
        user.setAvatarUrl(registrationDTO.getAvatarUrl());
        user.setUpdateTime(LocalDate.now());

        userDao.save(user);
        return "User updated successfully!";
    }

    /**
     * Deletes a user from the system.
     *
     * @param id The ID of the user to be deleted.
     * @return A success message indicating that the user was deleted successfully.
     * @throws CommonException If the user is not found.
     */
    public String deleteUser(String id) throws CommonException {
        User user = userDao.findById(id)
                .orElseThrow(() -> new CommonException(404, "User not found"));

        userDao.delete(user);
        return "User deleted successfully!";
    }

    /**
     * Finds a user by their ID.
     *
     * @param id The ID of the user to be found.
     * @return The User entity if found; null otherwise.
     */
    public User find(String id) {
        return userDao.findById(id).orElse(null);
    }

    /**
     * Retrieves all users from the system.
     *
     * @return A list of all users.
     */
    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * Retrieves a user by their nickname.
     *
     * @param nickName The nickname of the user to be retrieved.
     * @return An Optional containing the User if found; empty otherwise.
     */
    public Optional<User> getUserByNickName(String nickName) {
        System.out.println("Looking for user with nickName: " + nickName);
        return userDao.findByNickName(nickName);
    }
}
