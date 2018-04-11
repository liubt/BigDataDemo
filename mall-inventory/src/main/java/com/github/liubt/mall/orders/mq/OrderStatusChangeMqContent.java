package com.github.liubt.mall.orders.mq;

import io.github.rhwayfun.springboot.rocketmq.starter.constants.RocketMqContent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusChangeMqContent extends RocketMqContent {
    private String orderNo;
    private String status;
}
