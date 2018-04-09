package com.github.liubt.mall.orders.service;

public interface CacheService {

    /**
     * 出库
     *
     * @param goodsNo 商品编号
     * @param count 数量
     */
    long stockOut(String goodsNo, long count);

    /**
     * 入库
     *
     * @param goodsNo
     * @param count
     */
    long stockIn(String goodsNo, long count);
}