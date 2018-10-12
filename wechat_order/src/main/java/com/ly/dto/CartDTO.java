package com.ly.dto;

import lombok.Data;

/**
 * @author ly
 * @ 2018-09-13
 * 购物车
 */
@Data
public class CartDTO {
    //商品id
    private String productId;
    //商品数量
    private Integer productStock;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productStock) {
        this.productId = productId;
        this.productStock = productStock;
    }
}
