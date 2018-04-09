package com.github.liubt.mall.goods.dto;

import com.github.liubt.mall.goods.model.Goods;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class GoodsDTO {

    public GoodsDTO() {
    }

    public GoodsDTO(Goods goods) {
        this.id = goods.getId();
        this.no = goods.getNo();
        this.name = goods.getName();
    }

    private Long id;

    /**
     * 商品No
     */
    @NotBlank
    @Length(max = 30)
    private String no;

    /**
     * 商品名
     */
    @NotBlank
    @Length(max = 30)
    private String name;

}
