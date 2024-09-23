package com.project.project.service.EntityService;

import com.project.project.dao.AddressDao;
import com.project.project.entity.Address;
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
public class AddressService implements BaseService<Address, String> {

    @Autowired
    private AddressDao addressDao;

    @Override
    public Address find(String id) {
        return addressDao.findById(id).orElse(null);
    }

    @Override
    public List<Address> findAll() {
        return addressDao.findAll();
    }

    @Override
    public List<Address> findList(String[] ids) {
        List<String> idList = Arrays.asList(ids);
        return addressDao.findAllById(idList);
    }

    @Override
    public List<Address> findList(Iterable<String> ids) {
        return addressDao.findAllById(ids);
    }

    @Override
    public Page<Address> findAll(Pageable pageable) {
        return addressDao.findAll(pageable);
    }

    @Override
    public Page<Address> findAll(Specification<Address> spec, Pageable pageable) {
        return addressDao.findAll(spec, pageable);
    }

    @Override
    public Address findOne(Specification<Address> spec) {
        return addressDao.findOne(spec).orElse(null);
    }

    @Override
    public long count() {
        return addressDao.count();
    }

    @Override
    public long count(Specification<Address> spec) {
        return addressDao.count(spec);
    }

    @Override
    public boolean exists(String id) {
        return addressDao.existsById(id);
    }

    @Override
    public void save(Address entity) {
        if (entity.getId() == null) {
            String newId;
            do {
                newId = IdGenerator.generateId("Address");
            } while (addressDao.existsById(newId));
            entity.setId(newId);
        }
        addressDao.save(entity);
    }

    @Override
    public void save(List<Address> entities) {
        entities.forEach(entity -> {
            if (entity.getId() == null) {
                String newId;
                do {
                    newId = IdGenerator.generateId("Address");
                } while (addressDao.existsById(newId));
                entity.setId(newId);
            }
        });
        addressDao.saveAll(entities);
    }

    @Override
    public void update(Address entity) {
        addressDao.save(entity);
    }

    @Override
    public void delete(String id) {
        addressDao.deleteById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        addressDao.deleteAllById(ids);
    }

    @Override
    public void delete(Address[] entities) {
        addressDao.deleteAll(Arrays.asList(entities));
    }

    @Override
    public void delete(Iterable<Address> entities) {
        addressDao.deleteAll(entities);
    }

    @Override
    public void delete(Address entity) {
        addressDao.delete(entity);
    }

    @Override
    public void deleteAll() {
        addressDao.deleteAll();
    }

    @Override
    public List<Address> findList(Specification<Address> spec) {
        return addressDao.findAll(spec);
    }

    @Override
    public List<Address> findList(Specification<Address> spec, Sort sort) {
        return addressDao.findAll(spec, sort);
    }

    @Override
    public void flush() {
        addressDao.flush();
    }
}
