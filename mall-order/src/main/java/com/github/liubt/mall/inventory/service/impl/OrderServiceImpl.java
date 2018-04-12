package com.github.liubt.mall.inventory.service.impl;

import com.github.liubt.mall.inventory.constants.Constants;
import com.github.liubt.mall.inventory.model.GoodsOrder;
import com.github.liubt.mall.inventory.mq.OrderStatusChangeMqContent;
import com.github.liubt.mall.inventory.repository.GoodsOrderRepository;
import com.github.liubt.mall.inventory.service.InventoryFeignService;
import com.github.liubt.mall.inventory.service.OrderService;
import io.github.rhwayfun.springboot.rocketmq.starter.common.DefaultRocketMqProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private GoodsOrderRepository goodsOrderRepository;
    @Autowired
    private DefaultRocketMqProducer producer;
    @Autowired
    private InventoryFeignService inventoryFeignService;

    @Override
    public GoodsOrder create(String goodsNo, int count) {

        // 生成订单编号
        String orderNo = generateNo();

        // 调用出库
        boolean hasStock = inventoryFeignService.stockOut(orderNo, goodsNo, count);

        if(!hasStock) {
            return null;
        }

        // 写数据库
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setOrderNo(orderNo);
        goodsOrder.setGoodsNo(goodsNo);
        goodsOrder.setCount(count);
        goodsOrderRepository.save(goodsOrder);

        // 发消息。库存服务接到消息后，把订单状态变成已确认
        // 发送出库消息
        OrderStatusChangeMqContent content = new OrderStatusChangeMqContent();
        content.setOrderNo(orderNo);
        content.setStatus(Constants.ORDER_CHANGE_TYPE_CONFIRM);
        Message msg = new Message(
                Constants.TOPIC_ORDER,
                Constants.TAG_ORDER_STATUS_CHANGE,
                content.toString().getBytes());
        boolean isSuccess = producer.sendMsg(msg);
        if(!isSuccess) {
            log.error("消息发送失败");
            // TODO: 消息发送失败处理。写入错误日志，进行处理？
        }

        return goodsOrder;
    }

    private String generateNo() {
        // 限流1秒1次访问，不会重复
        return String.valueOf(System.currentTimeMillis());
    }
}
