package com.github.liubt.mall.inventory.mq;

import com.github.liubt.mall.inventory.constants.Constants;
import com.github.liubt.mall.inventory.enums.OrderStatusEnum;
import com.github.liubt.mall.inventory.model.GoodsOrder;
import com.github.liubt.mall.inventory.repository.GoodsOrderRepository;
import com.github.liubt.mall.inventory.service.CacheService;
import io.github.rhwayfun.springboot.rocketmq.starter.common.AbstractRocketMqConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderStatusChangeMqConsumer
        extends AbstractRocketMqConsumer<OrderMqTopic, OrderStatusChangeMqContent> {

    @Autowired
    private GoodsOrderRepository goodsOrderRepository;
    @Autowired
    private CacheService cacheService;

    @Override
    public Map<String, Set<String>> subscribeTopicTags() {
        Map<String, Set<String>> topicSetMap = new HashMap<>();
        Set<String> tagSet = new HashSet<>();
        tagSet.add(Constants.TAG_ORDER_STATUS_CHANGE);
        topicSetMap.put(Constants.TOPIC_ORDER, tagSet);
        return topicSetMap;
    }

    @Override
    public String getConsumerGroup() {
        return "order-status-consumer-group";
    }

    @Override
    public boolean consumeMsg(OrderStatusChangeMqContent content, MessageExt msg) {

        GoodsOrder order = goodsOrderRepository.findOneByOrderNo(content.getOrderNo());
        if(order == null) {
            logger.warn("订单不存在" + content.getOrderNo());
            return false;
        }

        if(Constants.ORDER_CHANGE_TYPE_CANCEL.equals(content.getStatus())) {
            this.cancel(order);
        } else if(Constants.ORDER_CHANGE_TYPE_CONFIRM.equals(content.getStatus())) {
            this.confirm(order);
        }

        // 返回true或false不会造成任何影响。调用处并未处理
        return false;
    }

    private void confirm(GoodsOrder order) {

        if(order.getStatus() != OrderStatusEnum.UNCONFIRMED) {
            throw new IllegalStateException();
        }

        order.setStatus(OrderStatusEnum.CONFIRMED);
        goodsOrderRepository.save(order);
    }

    private void cancel(GoodsOrder order) {

        if(order.getStatus() != OrderStatusEnum.CONFIRMED) {
            throw new IllegalStateException();
        }

        // 更新订单状态
        order.setStatus(OrderStatusEnum.CANCEL);
        order.setStatusUpdateTime(new Date());
        goodsOrderRepository.save(order);

        // 恢复库存
        cacheService.stockIn(order.getGoodsNo(),order.getCount());
    }

   
}
