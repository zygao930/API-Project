package com.project.project.service;

import com.project.project.dao.UserDao;
import com.project.project.entity.User;
import com.project.project.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements BaseService<User, String> {

    @Autowired
    private UserDao userDao;

    @Override
    public User find(String id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<User> findList(String[] ids) {
        List<String> idList = Arrays.asList(ids);
        return userDao.findAllById(idList);
    }

    @Override
    public List<User> findList(Iterable<String> ids) {
        return userDao.findAllById(ids);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override
    public Page<User> findAll(Specification<User> spec, Pageable pageable) {
        return userDao.findAll(spec, pageable);
    }

    @Override
    public User findOne(Specification<User> spec) {
        return userDao.findOne(spec).orElse(null);
    }

    @Override
    public long count() {
        return userDao.count();
    }

    @Override
    public long count(Specification<User> spec) {
        return userDao.count(spec);
    }

    @Override
    public boolean exists(String id) {
        return userDao.existsById(id);
    }

    @Override
    public void save(User entity) {
        if (entity.getId() == null) {
            String newId;
            do {
                newId = IdGenerator.generateId("User");
            } while (userDao.existsById(newId));
            entity.setId(newId);
        }
        userDao.save(entity);
    }

    @Override
    public void save(List<User> entities) {
        entities.forEach(entity -> {
            if (entity.getId() == null) {
                String newId;
                do {
                    newId = IdGenerator.generateId("User");
                } while (userDao.existsById(newId));
                entity.setId(newId);
            }
        });
        userDao.saveAll(entities);
    }

    @Override
    public User update(User entity) {
        // Assuming update is the same as save for simplicity
        return userDao.save(entity);
    }

    @Override
    public void delete(String id) {
        userDao.deleteById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        userDao.deleteAllById(ids);
    }

    @Override
    public void delete(User[] entities) {
        userDao.deleteAll(Arrays.asList(entities));
    }

    @Override
    public void delete(Iterable<User> entities) {
        userDao.deleteAll(entities);
    }

    @Override
    public void delete(User entity) {
        userDao.delete(entity);
    }

    @Override
    public void deleteAll() {
        userDao.deleteAll();
    }

    @Override
    public List<User> findList(Specification<User> spec) {
        return userDao.findAll(spec);
    }

    @Override
    public List<User> findList(Specification<User> spec, Sort sort) {
        return userDao.findAll(spec, sort);
    }

    @Override
    public void flush() {
        userDao.flush();
    }
}
