package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/get/{key}")
    public Object get(@PathVariable("key") String key){
        return redisTemplate.opsForValue().get(key);
    }
    @PostMapping("/set/{key}/{value}")
    public Object set(@PathVariable("key") String key,
                      @PathVariable("value") String value){
        redisTemplate.opsForValue().set(key,value);
        return "success";
    }

}
