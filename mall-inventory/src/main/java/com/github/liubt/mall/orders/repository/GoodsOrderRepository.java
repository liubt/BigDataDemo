package com.github.liubt.mall.orders.repository;

import com.github.liubt.mall.orders.model.GoodsOrder;
import org.springframework.data.repository.Repository;


public interface GoodsOrderRepository extends Repository<GoodsOrder, Long> {

    int countByNo(String no);

    GoodsOrder findOneByNo(String no);

    GoodsOrder save(GoodsOrder GoodsOrder);

}
