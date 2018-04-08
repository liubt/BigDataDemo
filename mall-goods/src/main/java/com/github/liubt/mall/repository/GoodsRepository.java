package com.github.liubt.mall.repository;

import com.github.liubt.mall.model.Goods;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.Repository;


@CacheConfig(cacheNames = "goods")
public interface GoodsRepository extends Repository<Goods, Long> {

    @Cacheable(value = "goods", key = "#p0")
    Goods findOneByNo(String no);

    @Cacheable("goods")
    Goods findByName(String name);

    @CachePut(value = "goods", key = "#p0.no")
    Goods save(Goods goods);

    @CacheEvict(value = "goods", key = "#goods.no")
    void delete(Goods goods);

}
