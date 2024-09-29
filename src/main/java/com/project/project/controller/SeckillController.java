package com.project.project.controller;

import com.project.project.service.Seckill.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling seckill (flash sale) related requests.
 * Provides endpoints to generate paths for seckill and execute the seckill process.
 */
@RestController
@RequestMapping("/api/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Generates a seckill path for the given user and goods.
     *
     * @param userId  the ID of the user participating in the seckill.
     * @param goodsId the ID of the goods being sold in the seckill.
     * @return a success message indicating the path was generated successfully.
     */
    @PostMapping("/pathGenerate")
    public String pathgenerate(@RequestParam String userId, @RequestParam String goodsId) {
        seckillService.generatePath(userId, goodsId);
        return "Path generated successfully";
    }

    /**
     * Executes the seckill process if the provided path is valid.
     *
     * @param path the generated path to validate and use for the seckill process.
     * @return a message indicating whether the seckill was successful or failed due to an invalid path.
     */
    @GetMapping("/seckillPath/{path}")
    public String seckill(@PathVariable String path) {
        if (redisTemplate.hasKey(path)) {
            // Path exists, execute seckill logic
            return "Seckill successful!";
        } else {
            // Path does not exist, return failure message
            return "Seckill failed, invalid path.";
        }
    }


}
