package com.github.liubt.mall.inventory.repository;

import com.github.liubt.mall.inventory.model.GoodsOrder;
import org.springframework.data.repository.Repository;


public interface GoodsOrderRepository extends Repository<GoodsOrder, Long> {

    int countByOrderNo(String no);

    GoodsOrder findOneByOrderNo(String orderNo);

    GoodsOrder save(GoodsOrder GoodsOrder);

}
