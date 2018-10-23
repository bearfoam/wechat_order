package com.ly.repository;

import com.ly.dataproject.SellerInfo;
import com.ly.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {
    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void save() throws Exception{
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setOpenid("abc");
        sellerInfo.setPassword("admin");
        sellerInfo.setUsername("admin");
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());

        SellerInfo result =  sellerInfoRepository.save(sellerInfo);
        Assert.assertNotNull(result);
    }


    @Test
    public void findByOpenid() throws Exception{
        SellerInfo result = sellerInfoRepository.findByOpenid("abc");
        Assert.assertEquals("abc",result.getOpenid());
    }
}