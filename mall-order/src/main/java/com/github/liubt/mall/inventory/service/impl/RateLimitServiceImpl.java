package com.github.liubt.mall.inventory.service.impl;

import com.github.liubt.mall.inventory.service.RateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RateLimitServiceImpl implements RateLimitService {

    private static final String CREATE_ORDER_TIME_KEY = "create_order_time_key";
    private static final String CREATE_ORDER_COUNTER_KEY = "create_order_counter_key";
    /**
     * 每秒1次
     */
    private static final long CREATE_ORDER_LIMIT  = 1;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean canCreateOrder() {
        if(!redisTemplate.hasKey(CREATE_ORDER_TIME_KEY) || !redisTemplate.hasKey(CREATE_ORDER_COUNTER_KEY)) {
            redisTemplate.opsForValue().set(CREATE_ORDER_TIME_KEY, "0" , 1, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(CREATE_ORDER_COUNTER_KEY, "0" ,2, TimeUnit.SECONDS);
        }
        if(redisTemplate.hasKey(CREATE_ORDER_TIME_KEY)
                && redisTemplate.opsForValue().increment(CREATE_ORDER_COUNTER_KEY,1L) > CREATE_ORDER_LIMIT) {
            return false;
        }
        
        return true;
    }
}
