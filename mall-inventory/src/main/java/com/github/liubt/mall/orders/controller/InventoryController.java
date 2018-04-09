package com.github.liubt.mall.orders.controller;

import com.github.liubt.mall.orders.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private CacheService cacheService;

    @RequestMapping(method = RequestMethod.GET)
    public String getGoods() {

        
        long a = cacheService.stockOut("g1", 10);
        System.out.println(a);
        a = cacheService.stockIn("g1", 10);
        System.out.println(a);
        a = cacheService.stockOut("g1", 5);
        System.out.println(a);

        return null;
    }
    

}
