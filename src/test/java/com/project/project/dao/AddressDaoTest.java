package com.project.project.dao;

import com.project.project.entity.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class AddressDaoTest {

    @Autowired
    private AddressDao addressDao;

    @Test
    public void testSaveAndFind() {
        // Arrange
        Address address = new Address();

        address.setAddressId("ADD4");
        address.setName("Joh");
        address.setPhone("12345678");
        address.setDetailedAddress("123 Test St");
        address.setState("CA");
        address.setProvinceId("123");
        address.setCityId("456");
        address.setAreaId("789");
        address.setCompletedAddress("1234 Main St");
        address.setCreateTime(LocalDate.now());
        address.setUpdateTime(LocalDate.now());

        // Act
        Address savedAddress = addressDao.save(address);
        Address foundAddress = addressDao.findById(savedAddress.getId()).orElse(null);

        // Assert
        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getName()).isEqualTo("Joh");
        assertThat(foundAddress.getPhone()).isEqualTo("1234567890");
        assertThat(foundAddress.getDetailedAddress()).isEqualTo("123 Test St");
        assertThat(foundAddress.getState()).isEqualTo("CA");
        assertThat(foundAddress.getProvinceId()).isEqualTo("123");
        assertThat(foundAddress.getCityId()).isEqualTo("456");
        assertThat(foundAddress.getAreaId()).isEqualTo("789");
        assertThat(foundAddress.getCompletedAddress()).isEqualTo("1234 Main St");
    }
}