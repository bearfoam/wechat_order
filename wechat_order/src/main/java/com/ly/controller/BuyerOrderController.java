package com.ly.controller;

import com.ly.converter.orderForm_orderDTO;
import com.ly.dataproject.OrderMaster;
import com.ly.dto.OrderDTO;
import com.ly.enums.ResultEnum;
import com.ly.exception.SellException;
import com.ly.form.OrderForm;
import com.ly.service.BuyerService;
import com.ly.service.OrderService;
import com.ly.utils.ResultVOUtil;
import com.ly.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ly
 * @ 2018-09-17
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        //判断表单验证有没有错误
        if(bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确, orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = orderForm_orderDTO.converter(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空");
            throw new SellException(ResultEnum.ORDER_CART);
        }
        OrderDTO result =  orderService.create(orderDTO);
        Map<String,String> map= new HashMap<String,String>();
        map.put("orderId",result.getOrderId());

        return ResultVOUtil.success(map);



    }



    //订单列表
    @GetMapping("/list")
    public  ResultVO<Page<OrderDTO>> list(@RequestParam("openId") String buyerOpenid,@RequestParam(value = "page",defaultValue = "0") Integer page,@RequestParam(value = "size",defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(buyerOpenid)){
            log.error("【查询订单列表】buyerOpenid 为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage =  orderService.findList(buyerOpenid,pageRequest);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @PostMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid")String buyerOpenid,@RequestParam("orderId") String orderId){
        //判断订单是否存在
        OrderDTO  orderDTO= buyerService.findOrderOne(buyerOpenid,orderId);
        return  ResultVOUtil.success(orderDTO);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid")String buyerOpenid,@RequestParam("orderId") String orderId){
        OrderDTO  orderDTO= buyerService.cancelOrderOne(buyerOpenid,orderId);
        return ResultVOUtil.success();

    }
}
