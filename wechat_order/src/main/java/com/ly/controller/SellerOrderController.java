package com.ly.controller;

import com.ly.dto.OrderDTO;
import com.ly.enums.ResultEnum;
import com.ly.exception.SellException;
import com.ly.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author ly
 * @ 2018-10-15
 */
@RequestMapping("/seller/order")
@Controller
@Slf4j
public class SellerOrderController {
    @Autowired
    OrderService orderService;

    /**
     * 订单列表
     * @param page 第几页
     * @param size 一页多少条
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView findAllList(@RequestParam(value="page",defaultValue = "1") Integer page, @RequestParam(value="size",defaultValue = "2") Integer size, Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<OrderDTO> orderDTOPage =  orderService.findAllList(pageRequest);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("order/list",map);
    }

    @GetMapping("/cancel")
    public ModelAndView cancelOrder(@RequestParam("orderId") String orderId,Map<String,Object> map){
        try {
            OrderDTO orderDTO =  orderService.findOne(orderId);
            orderService.cancel(orderDTO);

        }catch (SellException e){
            log.error("【卖家端取消订单】 取消发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg",ResultEnum.CANCEL_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/detail")
    public ModelAndView orderDetail(String orderId,Map<String,Object> map){
       try{
           OrderDTO orderDTO = orderService.findOne(orderId);
           map.put("orderDTO",orderDTO);
           return new ModelAndView("order/detail",map);
       }catch (SellException e){
           log.error("【卖家端查询订单详情】查询失败出现异常{}",e);
           map.put("msg",e.getMessage());
           map.put("url","/sell/seller/order/list");
           return new ModelAndView("common/error",map);
       }


    }
    @GetMapping("/finish")
    public ModelAndView orderFinish(@RequestParam("orderId") String orderId,Map<String,Object> map){
        try {
            OrderDTO orderDTO =  orderService.findOne(orderId);
            orderService.finish(orderDTO);

        }catch (SellException e){
            log.error("【卖家端完结订单】 订单状态异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.FINISH_ORDER_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }


}
