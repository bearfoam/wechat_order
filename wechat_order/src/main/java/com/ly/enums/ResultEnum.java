package com.ly.enums;

import lombok.Getter;

/**
 *
 */
@Getter
public enum ResultEnum {
    PARAM_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态错误"),
    ORDER_UPDATE_FAIL(15,"订单状态更改失败"),
    ORDER_DETAIL_EMPTY(19,"订单详情为空"),
    ORDER_PAID_STATUS_ERROR(16,"订单支付状态不正确"),
    ORDER_CART(17,"购物车为空"),
    ORDER_OPENID_ERROR(18,"该订单不属于当前用户"),
    WECHAT_MP_ERROR(20,"微信公众号方面错误"),
    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21,"微信支付异步通知金额验证不通过")
    ;


    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}