package com.github.liubt.mall.inventory.mq;

import com.github.liubt.mall.inventory.constants.Constants;
import com.github.liubt.mall.inventory.enums.OrderStatusEnum;
import com.github.liubt.mall.inventory.model.GoodsOrder;
import com.github.liubt.mall.inventory.repository.GoodsOrderRepository;
import io.github.rhwayfun.springboot.rocketmq.starter.common.AbstractRocketMqConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InventoryChangeMqConsumer
        extends AbstractRocketMqConsumer<OrderMqTopic, InventoryChangeMqContent> {

    @Autowired
    private GoodsOrderRepository goodsOrderRepository;

    @Override
    public Map<String, Set<String>> subscribeTopicTags() {
        Map<String, Set<String>> topicSetMap = new HashMap<>();
        Set<String> tagSet = new HashSet<>();
        tagSet.add(Constants.TAG_STOCK_OUT);
        tagSet.add(Constants.TAG_STOCK_IN);
        topicSetMap.put(Constants.TOPIC_ORDER, tagSet);
        return topicSetMap;
    }

    @Override
    public String getConsumerGroup() {
        return "order-consumer-group";
    }

    @Override
    public boolean consumeMsg(InventoryChangeMqContent content, MessageExt msg) {

        if(Constants.TAG_STOCK_OUT.equals(msg.getTags())) {
            this.stockOut(content);
        } else if(Constants.TAG_STOCK_IN.equals(msg.getTags())) {
            this.stockIn(content);
        }

        // 返回true或false不会造成任何影响。调用处并未处理
        return false;
    }

    private void stockOut(InventoryChangeMqContent content) {
        // 消息防止重复
        if(goodsOrderRepository.countByOrderNo(content.getOrderNo()) > 0) {
            return;
        }

        GoodsOrder order = new GoodsOrder();
        order.setOrderNo(content.getOrderNo());
        order.setGoodsNo(content.getGoodsNo());
        order.setCount(content.getCount());
        order.setStatus(OrderStatusEnum.UNCONFIRMED);
        order.setStatusUpdateTime(new Date());
        goodsOrderRepository.save(order);
    }

    private void stockIn(InventoryChangeMqContent content) {
        // TODO:
    }
}
