package com.ly.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ly.dataproject.OrderDetail;
import com.ly.enums.OrderStatusEnum;
import com.ly.enums.PayStatusEnum;
import com.ly.utils.EnumUtil;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author ly
 * @ 2018-09-13
 * 传输实体类
 */
@Data
public class OrderDTO {
    /**订单id*/
    private String orderId;

    /** 买家名字*/
    private String buyerName;

    /**买家手机号码 */
    private  String buyerPhone;

    /** 买家地址*/
    private String buyerAddress;

    /** 买家微信 */
    private  String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态 默认为新下单*/
    private Integer orderStatus;

    /** 支付状态 默认为未支付*/
    private  Integer payStatus;

    /**创建时间*/
    private Timestamp createTime;

    /**更新时间*/
    private Timestamp updateTime;

    //商品集合
    private List<OrderDetail> orderDetailList;

    @JsonIgnore  //该注解可以在转化为json对象输出的时候忽略这个字段
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }

}
