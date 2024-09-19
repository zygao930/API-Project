package com.project.project.security;

import com.project.project.exception.CommonException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public class CustomizeUserDetailsService implements UserDetailsService {
    // 用于模拟从数据库查询
    private final List<String> usernameList;

    public CustomizeUserDetailsService(List<String> usernameList) {
        this.usernameList = usernameList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        if(!exists(username)) {
            try {
                throw new CommonException(404,"用户不存在");
            } catch (CommonException e) {
                throw new RuntimeException(e);
            }
        }
        // 此处的TEST表示用户的权限, {xxx}指定密码加密的方式, {noop}表示不加密，采用明文
        return User.withUsername(username).authorities("TEST").password("{noop}123456").build();
    }

    private boolean exists(String username) {
        boolean exist = false;
        for(String item : usernameList) {
            if(item.equals(username)) {
                exist = true;
                break;
            }
        }
        return exist;
    }
}
