package com.project.project.service;

import com.project.project.dao.ProductDao;
import com.project.project.entity.Product;
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
public class ProductService implements BaseService<Product, String> {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product find(String id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public List<Product> findList(String[] ids) {
        List<String> idList = Arrays.asList(ids);
        return productDao.findAllById(idList);
    }

    @Override
    public List<Product> findList(Iterable<String> ids) {
        return productDao.findAllById(ids);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productDao.findAll(pageable);
    }

    @Override
    public Page<Product> findAll(Specification<Product> spec, Pageable pageable) {
        return productDao.findAll(spec, pageable);
    }

    @Override
    public Product findOne(Specification<Product> spec) {
        return productDao.findOne(spec).orElse(null);
    }

    @Override
    public long count() {
        return productDao.count();
    }

    @Override
    public long count(Specification<Product> spec) {
        return productDao.count(spec);
    }

    @Override
    public boolean exists(String id) {
        return productDao.existsById(id);
    }

    @Override
    public void save(Product entity) {
        if (entity.getId() == null) {
            String newId;
            do {
                newId = IdGenerator.generateId("Product");
            } while (productDao.existsById(newId));
            entity.setId(newId);
        }
        productDao.save(entity);
    }

    @Override
    public void save(List<Product> entities) {
        entities.forEach(entity -> {
            if (entity.getId() == null) {
                String newId;
                do {
                    newId = IdGenerator.generateId("Product");
                } while (productDao.existsById(newId));
                entity.setId(newId);
            }
        });
        productDao.saveAll(entities);
    }

    @Override
    public Product update(Product entity) {
        // Assuming update is the same as save for simplicity
        return productDao.save(entity);
    }

    @Override
    public void delete(String id) {
        productDao.deleteById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        productDao.deleteAllById(ids);
    }

    @Override
    public void delete(Product[] entities) {
        productDao.deleteAll(Arrays.asList(entities));
    }

    @Override
    public void delete(Iterable<Product> entities) {
        productDao.deleteAll(entities);
    }

    @Override
    public void delete(Product entity) {
        productDao.delete(entity);
    }

    @Override
    public void deleteAll() {
        productDao.deleteAll();
    }

    @Override
    public List<Product> findList(Specification<Product> spec) {
        return productDao.findAll(spec);
    }

    @Override
    public List<Product> findList(Specification<Product> spec, Sort sort) {
        return productDao.findAll(spec, sort);
    }

    @Override
    public void flush() {
        productDao.flush();
    }
}
