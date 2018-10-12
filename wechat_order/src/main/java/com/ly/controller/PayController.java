package com.ly.controller;

import com.lly835.bestpay.model.PayResponse;
import com.ly.dto.OrderDTO;
import com.ly.enums.ResultEnum;
import com.ly.exception.SellException;
import com.ly.service.OrderService;
import com.ly.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

import java.util.Map;

/**
 * @author ly
 * @ 2018-09-28
 */
@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    OrderService orderService;

    @Autowired
    PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map){
        //1.查询订单是否存在
        OrderDTO orderDTO =  orderService.findOne(orderId);
        if(orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.发起支付
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("pay/create",map);

    }

    /**
     * 微信异步通知支付结果
     * @param notifyData
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);

        //返回给微信处理结果,不要在提醒了，即不要发出异步通知了
        return new ModelAndView("pay/success");

    }




}
