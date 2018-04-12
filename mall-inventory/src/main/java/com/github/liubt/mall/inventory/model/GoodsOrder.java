package com.github.liubt.mall.inventory.model;

import com.github.liubt.mall.inventory.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    @Column(nullable = false, length = 30)
    private String goodsNo;

    /**
     * 商品数量
     */
    @NotNull
    private Integer count;

    /**
     * 订单状态
     * 刚刚创建时为unconfirmed。
     * order服务中订单创建成功时，会发消息。接到消息后变成confirmed
     * order服务中取消订单时，会发消息。接到消息后变成cancel
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    /**
     * 状态最后更新时间
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusUpdateTime;

    
}
