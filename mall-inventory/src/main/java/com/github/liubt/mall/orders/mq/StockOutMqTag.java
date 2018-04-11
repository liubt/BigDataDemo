package com.github.liubt.mall.orders.mq;

import com.github.liubt.mall.orders.constants.Constants;
import io.github.rhwayfun.springboot.rocketmq.starter.constants.RocketMqTag;

public class StockOutMqTag implements RocketMqTag {
    @Override
    public String getTag() {
        return Constants.TAG_STOCK_OUT;
    }
}
