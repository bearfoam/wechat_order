package com.ly.dataproject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

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
    private  Integer productStatus;
    //类别
    private Integer categoryType;
}
