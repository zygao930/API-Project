package com.project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing Redis operations.
 */
@RestController
@RequestMapping("/api/redis")
public class RedisConfigController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Sets a key-value pair in Redis.
     *
     * @param key   the key to be set in Redis.
     * @param value the value to be associated with the key.
     * @return a confirmation message indicating the value has been set.
     */
    @PostMapping("/set")
    public String set(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return "Value set in Redis";
    }

    /**
     * Retrieves the value associated with a key from Redis.
     *
     * @param key the key whose associated value is to be retrieved.
     * @return the value associated with the specified key.
     */
    @GetMapping("/get")
    public Object get(@RequestParam String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Renames an existing key in Redis.
     *
     * @param oldkey the current name of the key.
     * @param newkey the new name for the key.
     * @return the value associated with the new key after renaming.
     */
    @PostMapping("/rename")
    public Object rename(@RequestParam String oldkey, String newkey) {
        redisTemplate.rename(oldkey, newkey);
        return redisTemplate.opsForValue().get(newkey);
    }

    /**
     * Tests the HashMap operations in Redis by putting a value into a hash and retrieving it.
     *
     * @return a confirmation message including the retrieved value from the hash.
     */
    @GetMapping("/testHashmap")
    @ResponseBody
    public String testHashmap() {
        redisTemplate.opsForHash().put("hashmap", "name", "zhangsan");
        String values = (String) redisTemplate.opsForHash().get("hashmap", "name");
        System.out.println("Values in the hashmap after pushing: " + values);
        return "Values pushed to Redis: " + values;
    }
}
