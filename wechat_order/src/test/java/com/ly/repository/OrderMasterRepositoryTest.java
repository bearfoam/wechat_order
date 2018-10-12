package com.ly.repository;

import com.ly.dataproject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("ly");
        orderMaster.setBuyerPhone("18668215440");
        orderMaster.setBuyerAddress("杭州市上城区月明小区");
        orderMaster.setBuyerOpenid("LY0304");
        orderMaster.setOrderAmount(new BigDecimal(12.5));


       OrderMaster result =  orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);

    }



    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = new PageRequest(0,1);
        Page<OrderMaster> result= orderMasterRepository.findByBuyerOpenid("961556928",pageRequest);
        System.out.println(result.getTotalElements());
    }

}