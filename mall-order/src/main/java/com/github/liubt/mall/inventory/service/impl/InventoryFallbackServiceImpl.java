package com.github.liubt.mall.inventory.service.impl;

import com.github.liubt.mall.inventory.service.InventoryFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InventoryFallbackServiceImpl implements InventoryFeignService {


    @Override
    public boolean stockOut(String orderNo, String goodsNo, int count) {
        log.error("库存服务不可用！");
        return false;
    }
}
