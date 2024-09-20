package com.project.project.service.EntityService;
import com.project.project.dao.UserDao;
import com.project.project.entity.User;
import com.project.project.controller.VerificationController;
import com.project.project.exception.CommonException;
import com.project.project.util.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.project.util.IdGenerator.generateId;

@Service
public class UserService implements BaseService<User, String> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private VerificationController verificationService;

    public String registerNewUser(UserRegistrationDTO registrationDTO, String inputCode) throws CommonException {
        System.out.println("开始注册新用户: " + registrationDTO.getEmail());
        // Step 1: Verify the email and the input code
        boolean isValid = verificationService.verifyCode(registrationDTO.getEmail(), inputCode).hasBody();

        if(!isValid) {
            return "验证码无效";
        }

        // Step 2: Check if the email is already used
        if(userDao.existsByEmail(registrationDTO.getEmail())){
            throw new CommonException(100,"邮箱已使用");
        }
        // Create new User entity
        User user = new User();
        user.setPassword(registrationDTO.getPassWord());
        user.setEmail(registrationDTO.getEmail());
        user.setPaykey(registrationDTO.getPayKey());
        user.setGender(registrationDTO.getGender());
        user.setNickName(registrationDTO.getNickName());

        user.setId(generateId("User"));
        user.setUserId(UUID.randomUUID().toString());
        user.setBalance(new BigDecimal("0.00"));
        user.setCreateTime(LocalDate.now());
        user.setUpdateTime(LocalDate.now());
        userDao.save(user);

        return "用户注册成功！";
    }

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
                newId = generateId("User");
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
                    newId = generateId("User");
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

    public Optional<User> getUserByNickName(String nickName) {
        System.out.println("Looking for user with nickName: " + nickName);
        return userDao.findByNickName(nickName);
    }

}
