package com.github.liubt.mall.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class GoodsDTO {

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
