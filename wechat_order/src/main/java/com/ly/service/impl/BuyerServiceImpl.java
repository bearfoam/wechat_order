package com.ly.service.impl;

import com.ly.dto.OrderDTO;
import com.ly.enums.ResultEnum;
import com.ly.exception.SellException;
import com.ly.service.BuyerService;
import com.ly.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ly
 * @ 2018-09-18
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid,String orderId) {
       return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrderOne(String openid,String orderId) {
        OrderDTO orderDTO=checkOrderOwner( openid, orderId);
        if(orderDTO == null){
            log.error("【取消订单】 查不到订单,orderId={},orderDTO={}",orderId,orderDTO);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderDTO;
    }

    //将判断逻辑抽成公用方法
    private OrderDTO checkOrderOwner(String openid,String orderId){
        OrderDTO orderDTO =  orderService.findOne(orderId);
        if(orderDTO == null){
            return null;
        }
        if(!orderDTO.getBuyerOpenid().equals(openid)){
            log.error("【查询订单】 订单的openid不一致, openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OPENID_ERROR);
        }
        return orderDTO;
    }
}
