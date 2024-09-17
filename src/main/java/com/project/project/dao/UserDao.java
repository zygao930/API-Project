package com.project.project.dao;

import com.project.project.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseDao<User,String> {
   boolean existsByEmail(String email);
}
