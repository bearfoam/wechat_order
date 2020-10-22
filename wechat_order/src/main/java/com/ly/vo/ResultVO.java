package com.ly.vo;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * http返回最外层对象
 */

@Data
public class ResultVO <T> implements Serializable {

    private static final long serialVersionUID = 4666961250542323468L;
    /** 错误码*/
    private Integer code;

    /**提示信息*/
    private String msg;

    /**具体内容*/
    private T date;


}
