package com.project.project.service.Seckill;

import com.project.project.service.EntityService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Service class for handling seckill (flash sale) functionalities.
 * This includes generating and validating unique paths for users during a seckill event.
 */
@Service
public class SeckillService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ProductService productService;

    /**
     * Generates a unique path for a user and a specific goods item during a seckill event.
     * The path is stored in Redis with a set expiration time.
     *
     * @param userId   The ID of the user requesting the seckill path.
     * @param goodsId  The ID of the goods item for which the seckill path is generated.
     * @return A unique path represented as a UUID string.
     */
    public String generatePath(String userId, String goodsId) {
        String path = UUID.randomUUID().toString();
        String key = "SECKILL_PATH_" + userId + "_" + goodsId;

        // Store the generated path in Redis with an expiration time of 1 minute
        redisTemplate.opsForValue().set(key, path, 1, TimeUnit.MINUTES);

        return path;
    }

    /**
     * Validates the seckill path for a user and a specific goods item.
     * This method checks if the provided path matches the stored path in Redis.
     *
     * @param userId   The ID of the user requesting the validation.
     * @param goodsId  The ID of the goods item for which the seckill path is validated.
     * @param path     The path to validate against the stored path.
     * @return True if the provided path matches the stored path; false otherwise.
     */
    public boolean validatePath(String userId, String goodsId, String path) {
        String key = "SECKILL_PATH_" + userId + "_" + goodsId;
        Object storedPath = redisTemplate.opsForValue().get(key);

        return storedPath != null && storedPath.equals(path);
    }

}
