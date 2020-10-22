package com.ly.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 返回给前端的商品详情实体类
 */
@Data
public class ProductInfoVO implements Serializable {
    private static final long serialVersionUID = 2797049128388521873L;

    @JsonProperty("id")
    private Integer productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("descripition")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
