package com.project.project.dao;

import com.project.project.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends BaseDao<User,String> {
   boolean existsByEmail(String email);

   Optional<User> findByNickName(String nickName);
}
