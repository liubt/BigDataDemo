package com.github.liubt.mall.inventory.mq;

import com.github.liubt.mall.inventory.constants.Constants;
import io.github.rhwayfun.springboot.rocketmq.starter.constants.RocketMqTag;

public class OrderStatusChangeMqTag implements RocketMqTag {
    @Override
    public String getTag() {
        return Constants.TAG_ORDER_STATUS_CHANGE;
    }
}
