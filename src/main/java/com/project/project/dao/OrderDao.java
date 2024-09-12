package com.project.project.dao;

import com.project.project.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends BaseDao<Order, String> {
}
