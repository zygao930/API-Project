package com.project.project.dao;
import com.project.project.entity.Product;
import com.project.project.util.IdGenerator;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Test
    @Order(2)
    public void testSaveAndFind() {
        // Arrange
        Product product = new Product();
        product.setId(IdGenerator.generateId("Product"));

        product.setGoodsId("G123");
        product.setGoodsName("Test Product");
        product.setOriginalPrice(new BigDecimal("100.00"));
        product.setDiscountPrice(new BigDecimal("80.00"));
        product.setMasterImg("image_url.jpg");
        product.setIntro("This is a test product.");
        product.setAddress("Test Location");
        product.setBeginTime(LocalDate.of(2024, 1, 1));
        product.setEndTime(LocalDate.of(2024, 12, 31));
        product.setPostage(5);
        product.setInventory(100);
        product.setSaleVolume(10);
        product.setVideoUrl("video_url.mp4");
        product.setCreateTime(LocalDate.now());
        product.setUpdateTime(LocalDate.now());

        // Act
        Product savedProduct = productDao.save(product);
        Product foundProduct = productDao.findById(savedProduct.getId()).orElse(null);

        // Assert
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getGoodsId()).isEqualTo("G123");
        assertThat(foundProduct.getGoodsName()).isEqualTo("Test Product");
        assertThat(foundProduct.getOriginalPrice()).isEqualByComparingTo(new BigDecimal("100.00"));
        assertThat(foundProduct.getDiscountPrice()).isEqualByComparingTo(new BigDecimal("80.00"));
        assertThat(foundProduct.getMasterImg()).isEqualTo("image_url.jpg");
        assertThat(foundProduct.getIntro()).isEqualTo("This is a test product.");
        assertThat(foundProduct.getAddress()).isEqualTo("Test Location");
        assertThat(foundProduct.getBeginTime()).isEqualTo(LocalDate.of(2024, 1, 1));
        assertThat(foundProduct.getEndTime()).isEqualTo(LocalDate.of(2024, 12, 31));
        assertThat(foundProduct.getPostage()).isEqualTo(5);
        assertThat(foundProduct.getInventory()).isEqualTo(100);
        assertThat(foundProduct.getSaleVolume()).isEqualTo(10);
        assertThat(foundProduct.getVideoUrl()).isEqualTo("video_url.mp4");
    }
}
