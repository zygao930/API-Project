package com.project.project.dao;
import com.project.project.entity.Address;
import com.project.project.util.IdGenerator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class AddressDaoTest {

    @Autowired
    private AddressDao addressDao;

    @Disabled
    @Test
    public void testSaveAndFind() {
        // Arrange
        Address address = new Address();
        address.setId(IdGenerator.generateId("Address"));

        address.setAddressId("A9");
        address.setName("Bill");
        address.setPhone("1123456789");
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
        assertThat(foundAddress.getName()).isEqualTo("Bill");
        assertThat(foundAddress.getPhone()).isEqualTo("1123456789");
        assertThat(foundAddress.getDetailedAddress()).isEqualTo("123 Test St");
        assertThat(foundAddress.getState()).isEqualTo("CA");
        assertThat(foundAddress.getProvinceId()).isEqualTo("123");
        assertThat(foundAddress.getCityId()).isEqualTo("456");
        assertThat(foundAddress.getAreaId()).isEqualTo("789");
        assertThat(foundAddress.getCompletedAddress()).isEqualTo("1234 Main St");
    }
}