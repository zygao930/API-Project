package com.project.project.controller;

import com.project.project.entity.Product;
import com.project.project.exception.CommonException;
import com.project.project.service.EntityService.ProductService;
import com.project.project.util.ProductRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling product-related operations like adding, updating, and deleting products.
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Endpoint for adding a new product.
     *
     * @param registrationDTO Product registration data transfer object.
     * @return Success or failure response.
     */
    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody ProductRegistrationDTO registrationDTO) {
        try {
            productService.addNewProduct(registrationDTO);
            return ResponseEntity.ok("Product added successfully");
        } catch (CommonException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    /**
     * Endpoint for finding a product by goodsName.
     *
     * @param goodsName Product name.
     * @return Product details if found.
     */
    @GetMapping("/findByGoodsName")
    public ResponseEntity<String> findByGoodsName(@RequestParam String goodsName) {
        Optional<Product> product = productService.getProductByGoodsId(goodsName);
        if (product.isPresent()) {
            return ResponseEntity.ok("Product found: " + product.get().getGoodsName() +
                    ", Price: " + product.get().getOriginalPrice());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    /**
     * Endpoint for getting all products.
     *
     * @return List of all products.
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    /**
     * Endpoint for getting a product by ID.
     *
     * @param id Product ID.
     * @return Product details if found.
     */
    @GetMapping("/getProductById")
    public Product getProductById(@RequestParam String id) {
        return productService.find(id);
    }

    /**
     * Endpoint for updating an existing product.
     *
     * @param id              Product ID.
     * @param registrationDTO Product registration data transfer object.
     * @return Success or failure response.
     */
    @PostMapping("/updateProduct/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody ProductRegistrationDTO registrationDTO) {
        try {
            String response = productService.updateProduct(id, registrationDTO);
            return ResponseEntity.ok(response);
        } catch (CommonException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    /**
     * Endpoint for deleting a product.
     *
     * @param id Product ID.
     * @return Success or failure response.
     */
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully!");
        } catch (CommonException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}
