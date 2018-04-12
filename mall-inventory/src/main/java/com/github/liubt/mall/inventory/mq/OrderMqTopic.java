package com.github.liubt.mall.inventory.mq;

import com.github.liubt.mall.inventory.constants.Constants;
import io.github.rhwayfun.springboot.rocketmq.starter.constants.RocketMqTopic;

public class OrderMqTopic implements RocketMqTopic {
    @Override
    public String getTopic() {
        return Constants.TOPIC_ORDER;
    }
}
