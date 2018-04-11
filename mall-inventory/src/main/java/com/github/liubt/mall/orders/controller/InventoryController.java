package com.github.liubt.mall.orders.controller;

import com.github.liubt.mall.orders.constants.Constants;
import com.github.liubt.mall.orders.model.GoodsOrder;
import com.github.liubt.mall.orders.mq.InventoryChangeMqContent;
import com.github.liubt.mall.orders.repository.GoodsOrderRepository;
import com.github.liubt.mall.orders.service.CacheService;
import io.github.rhwayfun.springboot.rocketmq.starter.common.DefaultRocketMqProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private DefaultRocketMqProducer producer;


    @RequestMapping(value = "/out", method = RequestMethod.GET)
    public boolean stockOut(
            @RequestParam String orderNo,
            @RequestParam String goodsNo,
            @RequestParam int count) {

        // 更新缓存
        cacheService.stockOut(goodsNo, count);

        // 发送出库消息
        InventoryChangeMqContent content = new InventoryChangeMqContent();
        content.setOrderNo(orderNo);
        content.setGoodsNo(goodsNo);
        content.setCount(count);
        Message msg = new Message(
                Constants.TOPIC_ORDER,
                Constants.TAG_STOCK_OUT,
                content.toString().getBytes());
        boolean isSuccess = producer.sendMsg(msg);
        if(!isSuccess) {
            // TODO: 消息发送失败处理。写入错误日志，进行处理？
        }

        //
        return true;
    }

    @RequestMapping(value = "/in", method = RequestMethod.GET)
    public boolean stockIn(
            @RequestParam String goodsNo,
            @RequestParam int count) {

        // 更新缓存
        cacheService.stockIn(goodsNo, count);

        return true;
    }
}
