package com.project.project.dao;

import com.project.project.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends BaseDao<Address, String> {
}
