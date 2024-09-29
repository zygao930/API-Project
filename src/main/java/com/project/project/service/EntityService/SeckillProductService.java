package com.project.project.service.EntityService;

import com.project.project.dao.SeckillProductDao;
import com.project.project.entity.SeckillProduct;
import com.project.project.exception.CommonException;
import com.project.project.util.SeckillProductRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.project.project.util.IdGenerator.generateId;

/**
 * Service class for managing product-related operations such as adding, updating,
 * deleting, and retrieving products.
 */
@Service
public class SeckillProductService {

    @Autowired
    private SeckillProductDao seckillProductDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Adds a new product to the system.
     *
     * @param registrationDTO Data Transfer Object containing product registration information.
     * @return A success message indicating that the product was added successfully.
     * @throws CommonException If the product ID already exists.
     */
    public String addNewProduct(SeckillProductRegistrationDTO registrationDTO) throws CommonException {
        System.out.println("Adding new product: " + registrationDTO.getGoodsName());

        // Check if the product ID already exists
        if (seckillProductDao.existsById(registrationDTO.getGoodsName())) {
            throw new CommonException(100, "Goods ID already in use");
        }

        // Create new Product entity
        SeckillProduct product = new SeckillProduct();
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

        seckillProductDao.save(product);
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
    public String updateProduct(String id, SeckillProductRegistrationDTO registrationDTO) throws CommonException {
        SeckillProduct product = seckillProductDao.findById(id)
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

        seckillProductDao.save(product);
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
        SeckillProduct product = seckillProductDao.findById(id)
                .orElseThrow(() -> new CommonException(404, "Product not found"));

        seckillProductDao.delete(product);
        return "Product deleted successfully!";
    }

    /**
     * Finds a product by its ID.
     *
     * @param id The ID of the product to be found.
     * @return The Product entity if found; null otherwise.
     */
    public SeckillProduct find(String id) {
        return seckillProductDao.findById(id).orElse(null);
    }

    /**
     * Retrieves all products from the system.
     *
     * @return A list of all products.
     */
    public List<SeckillProduct> findAll() {
        return seckillProductDao.findAll();
    }

    /**
     * Retrieves a product by its goods ID.
     *
     * @param goodsId The goods ID of the product to be retrieved.
     * @return An Optional containing the Product if found; empty otherwise.
     */
    public Optional<SeckillProduct> getProductByGoodsId(String goodsId) {
        System.out.println("Looking for product with goodsId: " + goodsId);
        return seckillProductDao.findById(goodsId);
    }

    public List<SeckillProduct> getAllSeckillGoods(){
        return seckillProductDao.findAll();
    }


    /**
     * Loads seckill goods information from the database and pushes it to Redis.
     * This method is scheduled to run every day at 1 AM, retrieving all
     * seckill products and setting them in Redis with an appropriate expiration time.
     *
     * The expiration time is calculated as the number of days between the
     * beginning and ending time of the seckill period, converted to seconds.
     *
     * @return a message indicating the result of the operation,
     *         stating that the products have been pushed to Redis successfully.
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public String loadGoodsPushRedis() {
        List<SeckillProduct> seckillProducts = getAllSeckillGoods();
        for (SeckillProduct seckillProduct : seckillProducts) {
            String redisKey = "GOODS_" + seckillProduct.getGoodsId();
            LocalDate beginTime = seckillProduct.getBeginTime();
            LocalDate endTime = seckillProduct.getEndTime();
            // Calculate the number of days between beginTime and endTime
            long daysBetween = ChronoUnit.DAYS.between(beginTime, endTime);
            // Convert days to seconds for the expiration time
            long expirationTime = daysBetween * 24 * 60 * 60;

            // Check if the product already exists in Redis
            if (Boolean.FALSE.equals(redisTemplate.hasKey(redisKey))) {
                // Push the product to Redis
                redisTemplate.opsForValue().set(redisKey, seckillProduct);
                // Set the expiration time for the product in Redis
                redisTemplate.expire(redisKey, expirationTime, TimeUnit.SECONDS);
            }
        } return "Product pushed to Redis successfully!";
    }

}
