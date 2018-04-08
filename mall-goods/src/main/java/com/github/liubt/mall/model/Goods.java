package com.github.liubt.mall.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品No
     */
    @NotBlank
    @Length(max = 30)
    @Column(nullable = false, length = 30)
    private String no;

    /**
     * 商品名
     */
    @NotBlank
    @Length(max = 30)
    @Column(nullable = false, length = 30)
    private String name;

    
}
