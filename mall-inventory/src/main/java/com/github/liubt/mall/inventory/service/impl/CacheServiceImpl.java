package com.github.liubt.mall.inventory.service.impl;

import com.github.liubt.mall.inventory.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public long stockOut(String goodsNo, long count) {
        synchronized (goodsNo) {
            Long currentValue = (Long)redisTemplate.opsForValue().get(goodsNo);
            if (currentValue != null && currentValue >=count) {
                long newValue = currentValue - count;
                redisTemplate.opsForValue().getAndSet(goodsNo, newValue);
                return newValue;
            } else {
                return -1;
            }
        }
    }

    @Override
    public long stockIn(String goodsNo, long count) {
        synchronized (goodsNo) {
            if (redisTemplate.hasKey(goodsNo)) {
                long newValue = (long)redisTemplate.opsForValue().get(goodsNo) + count;
                redisTemplate.opsForValue().set(goodsNo, newValue);
                return newValue;
            } else {
                redisTemplate.opsForValue().set(goodsNo, count);
                return count;
            }
        }
    }

    @Override
    public long stockCount(String goodsNo) {
        Long currentValue = (Long)redisTemplate.opsForValue().get(goodsNo);
        if(currentValue == null) {
            return -1;
        } else {
            return currentValue;
        }
    }
}
