package com.project.project.dao;
import com.project.project.entity.Order;
import com.project.project.util.IdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void testSaveAndFind() {
        // Arrange
        Order order = new Order();
        order.setId(IdGenerator.generateId("Order"));
        order.setOrderId("O123456");
        order.setUserId("U123");
        order.setGoodsId("G123");
        order.setPurchase(new BigDecimal(1)); // Assuming a single unit purchase
        order.setAddressId("A5");
        order.setOrderState("Pending");
        order.setPaykey("PAY123");
        order.setTotalMoney(new BigDecimal(1000)); // Assuming 1000 currency units
        order.setCancelTime(LocalDate.now().plusDays(3));
        order.setCreateTime(LocalDate.now());
        order.setUpdateTime(LocalDate.now());

        // Act
        Order savedOrder = orderDao.save(order);
        Order foundOrder = orderDao.findById(savedOrder.getId()).orElse(null);

        // Assert
        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getOrderId()).isEqualTo("O123456");
        assertThat(foundOrder.getUserId()).isEqualTo("U123");
        assertThat(foundOrder.getGoodsId()).isEqualTo("G123");
        assertThat(foundOrder.getPurchase()).isEqualTo(new BigDecimal(1));
        assertThat(foundOrder.getAddressId()).isEqualTo("A5");
        assertThat(foundOrder.getOrderState()).isEqualTo("Pending");
        assertThat(foundOrder.getPaykey()).isEqualTo("PAY123");
        assertThat(foundOrder.getTotalMoney()).isEqualTo(new BigDecimal(1000));
        assertThat(foundOrder.getCancelTime()).isEqualTo(LocalDate.now().plusDays(3));
        assertThat(foundOrder.getCreateTime()).isEqualTo(LocalDate.now());
        assertThat(foundOrder.getUpdateTime()).isEqualTo(LocalDate.now());
    }
}
