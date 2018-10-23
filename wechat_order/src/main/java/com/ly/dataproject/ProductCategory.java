package com.ly.dataproject;

import com.sun.org.apache.bcel.internal.generic.DADD;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 类目表
 * @DynamicUpdate 该注解可以动态修改时间
 * @Data 可以生成get，set，tostring方法
 */

@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    //类目id
    @Id
    @GeneratedValue
    private Integer categoryId;
    //类目名字
    private String categoryName;

    //类目编号
    private Integer categoryType;

    /**创建时间*/
    private Timestamp createTime;

    /**更新时间*/
    private Timestamp updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
