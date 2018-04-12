package com.github.liubt.mall.inventory.mq;

import com.github.liubt.mall.inventory.constants.Constants;
import io.github.rhwayfun.springboot.rocketmq.starter.constants.RocketMqTag;

public class StockOutMqTag implements RocketMqTag {
    @Override
    public String getTag() {
        return Constants.TAG_STOCK_OUT;
    }
}
