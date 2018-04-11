package com.github.liubt.mall.orders.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class GoodsOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单号
     */
    @NotBlank
    @Length(max = 30)
    @Column(nullable = false, length = 30, unique = true)
    private String orderNo;

    /**
     * 商品编号
     */
    @NotBlank
    @Length(max = 30)
    @Column(nullable = false, length = 30, unique = true)
    private String goodsNo;

    /**
     * 商品数量
     */
    @NotNull
    private Integer count;

    /**
     * 是否已取消
     */
    private boolean cancel;


    
}
