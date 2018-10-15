package com.ly.service.impl;

import com.ly.dataproject.OrderDetail;
import com.ly.dto.OrderDTO;
import com.ly.enums.OrderStatusEnum;
import com.ly.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    OrderServiceImpl orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("肖元元");
        orderDTO.setBuyerPhone("17755897180");
        orderDTO.setBuyerAddress("安徽省金寨县南溪镇");
        orderDTO.setBuyerOpenid("17755897180");
        orderDTO.setOrderAmount(new BigDecimal(123));
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1");
        o1.setProductQuantity(2);
        orderDetailList.add(o1);
        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】 result{}",result);

    }

    @Test
    public void findOne() {
       OrderDTO result =  orderService.findOne("1536847078045161888");
        log.info("【单个订单】 result{}",result);

    }

    @Test
    public void findList() throws Exception{
        PageRequest pageRequest = new PageRequest(1,5);
        String buyerOpenid="961556928";
        Page<OrderDTO> orderDTOPage=orderService.findList(buyerOpenid,pageRequest);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());


    }

    @Test
    public void cancel() throws Exception {
       OrderDTO orderDTO  = orderService.findOne("1536847078045161888");
        OrderDTO result =  orderService.cancel(orderDTO);
        Assert.assertEquals(result.getOrderStatus(),OrderStatusEnum.CANCEL.getCode());

    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO =   orderService.findOne("1536847078045161888");
        OrderDTO result =  orderService.finish(orderDTO);
        Assert.assertEquals(result.getOrderStatus(),OrderStatusEnum.FINISHED.getCode());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO orderDTO =   orderService.findOne("1536847078045161888");
        OrderDTO result =  orderService.paid(orderDTO);
        Assert.assertEquals(result.getPayStatus(),PayStatusEnum.SUCCESS.getCode());
    }

    @Test
    public void findAllList() throws Exception{
        PageRequest pageRequest = new PageRequest(1,5);

        Page<OrderDTO> orderDTOPage=orderService.findAllList(pageRequest);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());


    }

}