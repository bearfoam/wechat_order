package com.ly.service.impl;

import com.ly.dataproject.ProductInfo;
import com.ly.dto.CartDTO;
import com.ly.enums.ProductStatusEnum;
import com.ly.enums.ResultEnum;
import com.ly.exception.SellException;
import com.ly.repository.ProductInfoRepository;
import com.ly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
   @Autowired
    private ProductInfoRepository productInfoRepository;


    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpALL() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findALL(org.springframework.data.domain.Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override //加库存
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock()+cartDTO.getProductStock();
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }

    }

    @Override //减库存
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo =   productInfoRepository.findOne(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock()-cartDTO.getProductStock();
            if(result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);

            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }
}
