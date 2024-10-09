//package com.project.project.controller;
//
//import org.redisson.api.RLock;
//
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/redisson")
//public class RedissonController {
//
//    @Autowired
//    private RedissonClient redissonClient;
//
//    @GetMapping("/getLock/{key}")
//    public String redissonTest(@PathVariable("key") String lockKey) {
//        RLock lock = redissonClient.getLock(lockKey);
//        try {
//            lock.lock();
//            Thread.sleep(10000);
//            return "Locked and processed";
//        } catch (Exception e) {
//            return "failed to process due to: " + e.getMessage();
//        } finally {
//            lock.unlock();
//            return "unlocked";
//        }
//    }
//}
//
