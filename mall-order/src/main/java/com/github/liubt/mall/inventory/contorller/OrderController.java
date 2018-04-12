package com.github.liubt.mall.inventory.contorller;

import com.github.liubt.mall.inventory.model.GoodsOrder;
import com.github.liubt.mall.inventory.service.OrderService;
import com.github.liubt.mall.inventory.service.RateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RateLimitService rateLimitService;

    @RequestMapping(method = RequestMethod.POST)
    public String create(
            @RequestParam String goodsNo,
            @RequestParam int count) {

        if(!rateLimitService.canCreateOrder()) {
            return "流量限制";
        }

        GoodsOrder goodsOrder = orderService.create(goodsNo, count);
        if(goodsOrder == null) {
            return "失败";
        } else {
            return "成功。订单号：" + goodsOrder.getOrderNo();
        }
    }

}
