package com.ldj.redis.service;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TemplateService {
    @Resource(name="redisTemplate")
    private RedisTemplate redisTemplate;

    @RequestMapping("/test")
    private String test() {
        ValueOperations stringOp = redisTemplate.opsForValue();
        String key = "applicatonName";
        if (redisTemplate.hasKey(key)) {
            String value = stringOp.get(key).toString();
            return "redis中查询得到的名称："+value;
        } else {
            String value = "数值";
            stringOp.set(key,value);
            return "数据库中查询得到的名称"+value;
        }
    }

}
