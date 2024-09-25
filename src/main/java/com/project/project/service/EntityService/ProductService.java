package com.project.project.service.EntityService;

import com.project.project.dao.ProductDao;
import com.project.project.entity.Product;
import com.project.project.exception.CommonException;
import com.project.project.util.ProductRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.project.util.IdGenerator.generateId;

/**
 * Service class for managing product-related operations such as adding, updating,
 * deleting, and retrieving products.
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    /**
     * Adds a new product to the system.
     *
     * @param registrationDTO Data Transfer Object containing product registration information.
     * @return A success message indicating that the product was added successfully.
     * @throws CommonException If the product ID already exists.
     */
    public String addNewProduct(ProductRegistrationDTO registrationDTO) throws CommonException {
        System.out.println("Adding new product: " + registrationDTO.getGoodsName());

        // Check if the product ID already exists
        if (productDao.existsById(registrationDTO.getGoodsName())) {
            throw new CommonException(100, "Goods ID already in use");
        }

        // Create new Product entity
        Product product = new Product();
        product.setGoodsName(registrationDTO.getGoodsName());
        product.setOriginalPrice(registrationDTO.getOriginalPrice());
        product.setDiscountPrice(registrationDTO.getDiscountPrice());
        product.setMasterImg(registrationDTO.getMasterImg());
        product.setIntro(registrationDTO.getIntro());
        product.setAddress(registrationDTO.getAddress());
        product.setBeginTime(registrationDTO.getBeginTime());
        product.setEndTime(registrationDTO.getEndTime());
        product.setPostage(registrationDTO.getPostage());
        product.setInventory(registrationDTO.getInventory());
        product.setSaleVolume(registrationDTO.getSaleVolume());
        product.setVideoUrl(registrationDTO.getVideoUrl());

        product.setId(generateId("Product"));
        product.setGoodsId(UUID.randomUUID().toString());
        product.setCreateTime(LocalDate.now());
        product.setUpdateTime(LocalDate.now());

        productDao.save(product);
        return "Product added successfully!";
    }

    /**
     * Updates an existing product in the system.
     *
     * @param id              The ID of the product to be updated.
     * @param registrationDTO Data Transfer Object containing updated product information.
     * @return A success message indicating that the product was updated successfully.
     * @throws CommonException If the product is not found.
     */
    public String updateProduct(String id, ProductRegistrationDTO registrationDTO) throws CommonException {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new CommonException(404, "Product not found"));

        product.setGoodsName(registrationDTO.getGoodsName());
        product.setOriginalPrice(registrationDTO.getOriginalPrice());
        product.setDiscountPrice(registrationDTO.getDiscountPrice());
        product.setMasterImg(registrationDTO.getMasterImg());
        product.setIntro(registrationDTO.getIntro());
        product.setAddress(registrationDTO.getAddress());
        product.setBeginTime(registrationDTO.getBeginTime());
        product.setEndTime(registrationDTO.getEndTime());
        product.setPostage(registrationDTO.getPostage());
        product.setInventory(registrationDTO.getInventory());
        product.setSaleVolume(registrationDTO.getSaleVolume());
        product.setVideoUrl(registrationDTO.getVideoUrl());
        product.setUpdateTime(LocalDate.now());

        productDao.save(product);
        return "Product updated successfully!";
    }

    /**
     * Deletes a product from the system.
     *
     * @param id The ID of the product to be deleted.
     * @return A success message indicating that the product was deleted successfully.
     * @throws CommonException If the product is not found.
     */
    public String deleteProduct(String id) throws CommonException {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new CommonException(404, "Product not found"));

        productDao.delete(product);
        return "Product deleted successfully!";
    }

    /**
     * Finds a product by its ID.
     *
     * @param id The ID of the product to be found.
     * @return The Product entity if found; null otherwise.
     */
    public Product find(String id) {
        return productDao.findById(id).orElse(null);
    }

    /**
     * Retrieves all products from the system.
     *
     * @return A list of all products.
     */
    public List<Product> findAll() {
        return productDao.findAll();
    }

    /**
     * Retrieves a product by its goods ID.
     *
     * @param goodsId The goods ID of the product to be retrieved.
     * @return An Optional containing the Product if found; empty otherwise.
     */
    public Optional<Product> getProductByGoodsId(String goodsId) {
        System.out.println("Looking for product with goodsId: " + goodsId);
        return productDao.findById(goodsId);
    }
}
