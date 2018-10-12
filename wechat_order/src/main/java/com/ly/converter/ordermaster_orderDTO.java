package com.ly.converter;

import com.ly.dataproject.OrderMaster;
import com.ly.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ly
 * @ 2018-09-13
 */
public class ordermaster_orderDTO {

    public static OrderDTO converter (OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }
    public static List<OrderDTO> converter(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e -> converter(e)).collect(Collectors.toList());
    }
}
