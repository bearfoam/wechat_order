package com.ly.dataproject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ly.enums.OrderStatusEnum;
import com.ly.enums.PayStatusEnum;
import com.ly.enums.ProductStatusEnum;
import com.ly.enums.ResultEnum;
import com.ly.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {
    @Id
    private String productId;
    //商品名字
    private String productName;
    //商品价格
    private BigDecimal productPrice;
    //库存
    private Integer productStock;
    //商品描述
    private  String productDescription;
    //商品小图
    private String productIcon;
    //商品状态
    private  Integer productStatus=ProductStatusEnum.UP.getCode();
    //类别
    private Integer categoryType;

    /**创建时间*/
    private Timestamp createTime;

    /**更新时间*/
    private Timestamp updateTime;

    @JsonIgnore  //该注解可以在转化为json对象输出的时候忽略这个字段
    @Transient
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }

}
