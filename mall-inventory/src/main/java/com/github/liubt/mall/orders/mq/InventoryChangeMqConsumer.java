package com.github.liubt.mall.orders.mq;

import com.github.liubt.mall.orders.constants.Constants;
import com.github.liubt.mall.orders.model.GoodsOrder;
import com.github.liubt.mall.orders.repository.GoodsOrderRepository;
import io.github.rhwayfun.springboot.rocketmq.starter.common.AbstractRocketMqConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        // 写数据库
        GoodsOrder order = new GoodsOrder();
        order.setOrderNo(content.getOrderNo());
        order.setGoodsNo(content.getGoodsNo());
        order.setCount(content.getCount());
        goodsOrderRepository.save(order);
    }

    private void stockIn(InventoryChangeMqContent content) {
        // TODO:
    }
}
