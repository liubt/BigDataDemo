package com.github.liubt.mall.goods.repository;

import com.github.liubt.mall.goods.model.Goods;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.Repository;


@CacheConfig(cacheNames = "goods")
public interface GoodsRepository extends Repository<Goods, Long> {

    @Cacheable(value = "goods", key = "#p0")
    Goods findOneByNo(String no);

    @Cacheable(value= "goods", key= "#p0")
    Goods findByName(String name);

    @CachePut(value = "goods", key = "#p0.no")
    Goods save(Goods goods);

    @CacheEvict(value = "goods", key = "#p0.no")
    void delete(Goods goods);

}
