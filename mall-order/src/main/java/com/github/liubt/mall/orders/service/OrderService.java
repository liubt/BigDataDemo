package com.github.liubt.mall.orders.service;

import com.github.liubt.mall.orders.model.GoodsOrder;

public interface OrderService {

    /**
     * 创建订单
     *
     * @param goodsNo 商品编号
     * @param count 数量
     */
    GoodsOrder create(String goodsNo, int count);

   
}
