package com.project.project.service;

import com.project.project.dao.AddressDao;
import com.project.project.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AddressService {

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private DataSource dataSource;

    @Transactional
    public Address saveAndReturnWithId(Address address) {
        addressDao.save(address);

        // Retrieve the generated ID from the database
        String id = retrieveGeneratedId(address);

        // Update the entity with the generated ID
        address.setId(id);
        return addressDao.save(address);
    }

    private String retrieveGeneratedId(Address address) {
        String id = null;
        // Assuming you have a JdbcTemplate or a Connection bean available
        try (Connection connection = dataSource.getConnection();
                     PreparedStatement statement = connection.prepareStatement("SELECT LAST_INSERT_ID()")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getString(1); // Retrieve the generated ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly
        }
        return id;
    }
}
