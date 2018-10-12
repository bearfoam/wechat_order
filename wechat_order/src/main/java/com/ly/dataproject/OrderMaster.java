package com.ly.dataproject;

import com.ly.enums.OrderStatusEnum;
import com.ly.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * 买家订单主表实体
 */
@Entity
@DynamicUpdate
@Data
public class OrderMaster {
    /**订单id*/
    @Id
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
    private Integer orderStatus=OrderStatusEnum.NEW.getCode();

    /** 支付状态 默认为未支付*/
    private  Integer payStatus=PayStatusEnum.WAIT.getCode();

    /**创建时间*/
    private Timestamp createTime;

    /**更新时间*/
    private Timestamp updateTime;

//    @Transient 这个注解可以使在跟数据表对应的时候忽略这个字段
//    private List<OrderDetail> orderDetailList;



}
