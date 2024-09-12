package com.project.project.dao;

import com.project.project.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends BaseDao<Product,Long> {

}
