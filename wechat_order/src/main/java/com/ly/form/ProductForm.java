package com.ly.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ly.enums.ProductStatusEnum;
import com.ly.utils.EnumUtil;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author ly
 * @ 2018-10-16
 */
@Data
public class ProductForm {

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
    //类别
    private Integer categoryType;


}
