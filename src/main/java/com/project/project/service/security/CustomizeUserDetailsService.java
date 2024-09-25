package com.project.project.service.security;

import com.project.project.dao.UserDao; // Import your UserDao
import com.project.project.entity.User; // Import your User entity
import com.project.project.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This service class provides custom implementation of Spring Security's {@link UserDetailsService}.
 * It loads user-specific data from the database based on the nickname of the user.
 * <p>
 * The {@link CustomizeUserDetailsService} interacts with the {@link UserDao} to retrieve user details and constructs
 * a {@link UserDetails} object which is then used by Spring Security for authentication and authorization.
 */
@Service // Annotate the class to make it a Spring Service
public class CustomizeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao; // Inject the UserDao

    /**
     * Loads a user from the database based on the provided nickname.
     *
     * @param nickName The nickname of the user to load.
     * @return A fully populated {@link UserDetails} object for Spring Security authentication.
     * @throws UsernameNotFoundException if a user with the provided nickname is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        // Retrieve the user from the database using the provided nickname
        User user = userDao.findByNickName(nickName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Build and return a UserDetails object for authentication
        return org.springframework.security.core.userdetails.User.withUsername(user.getNickName())
                .password(user.getPassword())
                .authorities("ROLE_USER") // Assign user role
                .build();
    }
}
