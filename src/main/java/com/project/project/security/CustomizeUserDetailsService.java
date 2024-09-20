package com.project.project.security;

import com.project.project.dao.UserDao; // Import your UserDao
import com.project.project.entity.User; // Import your User entity
import com.project.project.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Annotate the class to make it a Spring Service
public class CustomizeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao; // Inject the UserDao

    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        User user = userDao.findByNickName(nickName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.withUsername(user.getNickName())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .build();
    }

}
