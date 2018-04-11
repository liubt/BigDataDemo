package com.github.liubt.mall.orders.mq;

import com.github.liubt.mall.orders.constants.Constants;
import io.github.rhwayfun.springboot.rocketmq.starter.constants.RocketMqTag;

public class OrderStatusChangeMqTag implements RocketMqTag {
    @Override
    public String getTag() {
        return Constants.TAG_ORDER_STATUS_CHANGE;
    }
}
