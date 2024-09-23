package com.project.project.service.EntityService;

import com.project.project.dao.OrderDao;
import com.project.project.entity.Order;
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
public class OrderService implements BaseService<Order, String> {

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order find(String id) {
        return orderDao.findById(id).orElse(null);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public List<Order> findList(String[] ids) {
        List<String> idList = Arrays.asList(ids);
        return orderDao.findAllById(idList);
    }

    @Override
    public List<Order> findList(Iterable<String> ids) {
        return orderDao.findAllById(ids);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderDao.findAll(pageable);
    }

    @Override
    public Page<Order> findAll(Specification<Order> spec, Pageable pageable) {
        return orderDao.findAll(spec, pageable);
    }

    @Override
    public Order findOne(Specification<Order> spec) {
        return orderDao.findOne(spec).orElse(null);
    }

    @Override
    public long count() {
        return orderDao.count();
    }

    @Override
    public long count(Specification<Order> spec) {
        return orderDao.count(spec);
    }

    @Override
    public boolean exists(String id) {
        return orderDao.existsById(id);
    }


    @Override
    public void save(Order entity) {
        if (entity.getId() == null) {
            String newId;
            do {
                newId = IdGenerator.generateId("Order");
            } while (orderDao.existsById(newId));
            entity.setId(newId);
        }
        orderDao.save(entity);
    }

    @Override
    public void save(List<Order> entities) {
        entities.forEach(entity -> {
            if (entity.getId() == null) {
                String newId;
                do {
                    newId = IdGenerator.generateId("Order");
                } while (orderDao.existsById(newId));
                entity.setId(newId);
            }
        });
        orderDao.saveAll(entities);
    }

    @Override
    public void update(Order entity) {
        // Assuming update is the same as save for simplicity
       orderDao.save(entity);
    }

    @Override
    public void delete(String id) {
        orderDao.deleteById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        orderDao.deleteAllById(ids);
    }

    @Override
    public void delete(Order[] entities) {
        orderDao.deleteAll(Arrays.asList(entities));
    }

    @Override
    public void delete(Iterable<Order> entities) {
        orderDao.deleteAll(entities);
    }

    @Override
    public void delete(Order entity) {
        orderDao.delete(entity);
    }

    @Override
    public void deleteAll() {
        orderDao.deleteAll();
    }

    @Override
    public List<Order> findList(Specification<Order> spec) {
        return orderDao.findAll(spec);
    }

    @Override
    public List<Order> findList(Specification<Order> spec, Sort sort) {
        return orderDao.findAll(spec, sort);
    }

    @Override
    public void flush() {
        orderDao.flush();
    }
}
