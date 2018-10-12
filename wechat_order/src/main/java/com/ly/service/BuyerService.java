package com.ly.service;

import com.ly.dto.OrderDTO;

/**
 * 验证订单是否为本人订单
 */
public interface BuyerService {

    /**
     * 查询一个订单
     * @return
     */
     OrderDTO findOrderOne(String openid,String orderId);

     //取消一个订单
     OrderDTO cancelOrderOne(String openid,String orderId);
}
