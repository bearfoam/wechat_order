package com.ly.service.impl;

import com.ly.dataproject.ProductInfo;
import com.ly.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    ProductServiceImpl productService;

    @Test
    public void findOne() throws Exception{
       ProductInfo productInfo = productService.findOne("1");
        Assert.assertEquals("1",productInfo.getProductId());
    }

    @Test
    public void findUpALL() throws Exception{
        List<ProductInfo> list = productService.findUpALL();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findALL() throws Exception{
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> productInfoPage =  productService.findALL(pageRequest);
        System.out.println(productInfoPage.getTotalElements());

    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("2");
        productInfo.setProductName("尖椒鸡蛋");
        productInfo.setProductPrice(new BigDecimal(15));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("good!");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(4);

        ProductInfo result  = productService.save(productInfo);
        Assert.assertNotNull(result);

    }

}