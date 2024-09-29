package com.project.project.dao;

import com.project.project.entity.Address;
import com.project.project.entity.Product;
import com.project.project.entity.SeckillProduct;
import org.springframework.stereotype.Repository;

@Repository
public interface SeckillProductDao extends BaseDao<SeckillProduct, String> {
}