package com.ly.service;

import com.ly.dataproject.ProductInfo;
import com.ly.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    /**
     * 查询所有商品
     * @return  List<ProductInfo>
     */
    List<ProductInfo> findUpALL();
    //分页
    Page<ProductInfo> findALL(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}
