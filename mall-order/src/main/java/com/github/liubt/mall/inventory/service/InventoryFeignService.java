package com.github.liubt.mall.inventory.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(serviceId = "inventory")
public interface InventoryFeignService {

    @RequestMapping(value = "/inventory/out", method = RequestMethod.GET)
    boolean stockOut(
            @RequestParam("orderNo") String orderNo,
            @RequestParam("goodsNo") String goodsNo,
            @RequestParam("count") int count);

   
}
