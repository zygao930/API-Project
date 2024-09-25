package com.project.project.controller;

import com.project.project.entity.User;
import com.project.project.exception.CommonException;
import com.project.project.service.EntityService.UserService;
import com.project.project.util.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling user-related operations like registration, retrieval, update, and deletion.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint for registering a new user.
     *
     * @param registrationDTO User registration data transfer object containing user details.
     * @param inputCode       Verification code for the user registration.
     * @return ResponseEntity with success message or error message.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO registrationDTO, String inputCode) {
        try {
            userService.registerNewUser(registrationDTO, inputCode);
            return ResponseEntity.ok("用户注册成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint for finding a user by their nickname.
     *
     * @param nickName the nickname of the user to be searched.
     * @return ResponseEntity with user details or a not found message.
     */
    @GetMapping("/findByNickName")
    public ResponseEntity<String> findByNickName(@RequestParam String nickName) {
        Optional<User> user = userService.getUserByNickName(nickName);
        if (user.isPresent()) {
            // Return more user details if necessary
            return ResponseEntity.ok("User found: " + user.get().getNickName() + ", Email: " + user.get().getEmail());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    /**
     * Endpoint for retrieving all users.
     *
     * @return List of all registered users.
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    /**
     * Endpoint for retrieving a user by their ID.
     *
     * @param id the ID of the user to be retrieved.
     * @return the User object associated with the given ID.
     */
    @GetMapping("/getUserById")
    public User getUserById(@RequestParam String id) {
        return userService.find(id);
    }

    /**
     * Endpoint for adding a new user.
     *
     * @param registrationDTO User registration data transfer object containing user details.
     * @return ResponseEntity with success message or error message.
     */
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserRegistrationDTO registrationDTO) {
        try {
            String response = userService.addUser(registrationDTO);
            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    /**
     * Endpoint for updating an existing user.
     *
     * @param id              the ID of the user to be updated.
     * @param registrationDTO User registration data transfer object containing updated user details.
     * @return ResponseEntity with success message or error message.
     */
    @PostMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRegistrationDTO registrationDTO) {
        try {
            String response = userService.updateUser(id, registrationDTO);
            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    /**
     * Endpoint for deleting a user by their ID.
     *
     * @param id the ID of the user to be deleted.
     * @return ResponseEntity with a success message or error message.
     */
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (CommonException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}
