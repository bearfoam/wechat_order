package com.ly.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.ly.dto.OrderDTO;
import com.ly.enums.ResultEnum;
import com.ly.exception.SellException;
import com.ly.service.OrderService;
import com.ly.service.PayService;
import com.ly.utils.JsonUtil;
import com.ly.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author ly
 * @ 2018-09-28
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME = "微信点餐";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    /**
     * 微信支付
     * @param orderDTO
     * @return
     */
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付，request={}", payRequest);

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付 】发起支付，response={}", JsonUtil.toJson(payResponse));

        return payResponse;
    }

    /**
     * 微信异步通知支付结果
     * @param notifyData
     * @return
     */
    @Override
    public PayResponse notify(String notifyData) {
        //1.验证签名，判断这个请求是否为微信发出的
        // 2.支付的状态
        //3.支付金额核对
        //4.支付人（下单人 == 支付人 看你的业务是否允许下单和支付为同一个人）
      PayResponse payResponse=  bestPayService.asyncNotify(notifyData);
      log.info("【微信支付】异步通知，payResponse={}",JsonUtil.toJson(payResponse));
      //修改订单支付状态

        //查询订单
        OrderDTO orderDTO =   orderService.findOne(payResponse.getOrderId());

        //判断订单是否存在
        if(orderDTO == null){
            log.error("【微信支付】异步通知，订单不存在，orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断金额是否一致，注意两个金额的类型不一样不能直接比较 (eg:0.1 和0.10)
        if(!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
           log.error("【微信支付】异步通知，订单金额不一致,orderId={},微信通知金额={},系统金额={}",payResponse.getOrderId(),payResponse.getOrderAmount(),orderDTO.getOrderAmount());
           throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
       //修改订单的支付状态
        orderService.paid(orderDTO);

        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     * @return
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】 request={}",refundRequest);

        RefundResponse refundResponse =  bestPayService.refund(refundRequest);
        log.info("【微信退款】 response={}",refundResponse);

        return refundResponse;
    }

}