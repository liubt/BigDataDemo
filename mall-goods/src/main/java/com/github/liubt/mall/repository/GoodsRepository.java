package com.github.liubt.mall.repository;

import com.github.liubt.mall.model.Goods;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.Repository;


@CacheConfig(cacheNames = "goods")
public interface GoodsRepository extends Repository<Goods, Long> {

    @Cacheable("goods")
    Goods findOneByNo(String no);

    @Cacheable("goods")
    Goods findByName(String name);

    Goods save(Goods goods);

    void delete(Goods goods);

}
