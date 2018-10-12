package com.ly.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ly.dataproject.OrderDetail;
import com.ly.dto.OrderDTO;
import com.ly.enums.ResultEnum;
import com.ly.exception.SellException;
import com.ly.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ly
 * @ 2018-09-17
 * 将前端请求参数转化为接口所需参数类型
 */
@Slf4j
public class orderForm_orderDTO {

    public static OrderDTO converter(OrderForm orderForm){
      //谷歌的一个插件可以将符合json格式的字符串转化为其他格式 ，如list
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        try{
        //将前端传过来的购物车字符串转化为list集合
        orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());}
        catch(Exception e){
            log.error("【对象转化】 错误 String={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
