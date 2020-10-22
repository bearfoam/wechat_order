package com.ly.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.EAN;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 2184236074750219726L;
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
