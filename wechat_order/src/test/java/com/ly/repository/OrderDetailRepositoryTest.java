package com.ly.repository;

import com.ly.dataproject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    OrderDetailRepository orderDetailRepository;


    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("2");
        orderDetail.setOrderId("2");
        orderDetail.setProductIcon("http://wechat/order");
        orderDetail.setProductId("2");
        orderDetail.setProductName("香菇滑鸡粥");
        orderDetail.setProductPrice(new BigDecimal(3.2));
        orderDetail.setProductQuantity(1);

        OrderDetail result = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(result);
    }


    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList =  orderDetailRepository.findByOrderId("1");
        Assert.assertNotEquals(0,orderDetailList.size());
    }
}