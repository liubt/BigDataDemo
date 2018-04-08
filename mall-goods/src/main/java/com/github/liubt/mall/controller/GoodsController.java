package com.github.liubt.mall.controller;

import com.github.liubt.mall.dto.GoodsDTO;
import com.github.liubt.mall.dto.ResultDTO;
import com.github.liubt.mall.model.Goods;
import com.github.liubt.mall.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @RequestMapping(value = "/{no}", method = RequestMethod.GET)
    public ResultDTO<GoodsDTO> getGoods(@PathVariable String no) {

        Goods goods = goodsRepository.findOneByNo(no);
        if(goods == null) {
            return ResultDTO.fail(new GoodsDTO());
        }

        GoodsDTO dto = new GoodsDTO(goods);
        return ResultDTO.success(dto);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResultDTO<GoodsDTO> newGoods(
            @RequestParam String no, @RequestParam String name) {

        Goods goods = new Goods();
        goods.setNo(no);
        goods.setName(name);
        goodsRepository.save(goods);

        GoodsDTO dto = new GoodsDTO(goods);
        return ResultDTO.success(dto);
    }

}
