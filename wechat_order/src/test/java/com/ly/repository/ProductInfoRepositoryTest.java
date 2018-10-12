package com.ly.repository;

import com.ly.dataproject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository infoRepository;

    @Test
    public void save() throws Exception{
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1");
        productInfo.setProductName("西红柿炒鸡蛋");
        productInfo.setProductPrice(new BigDecimal(12.5));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("good!");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(3);

        ProductInfo result= infoRepository.save(productInfo);
        Assert.assertNotNull(result);
    }
}