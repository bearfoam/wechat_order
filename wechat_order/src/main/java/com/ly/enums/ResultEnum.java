package com.ly.enums;

import lombok.Getter;

/**
 *
 */
@Getter
public enum ResultEnum {
    SUCCESS(0,"成功"),
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
    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21,"微信支付异步通知金额验证不通过"),
    CANCEL_SUCCESS(22,"订单取消成功"),
    FINISH_ORDER_SUCCESS(23,"订单完结成功"),
    PRODUCT_STATUS_ERROR(24,"商品状态错误"),
    PRODUCT_ONSALE_SUCCESS(25,"商品上架成功"),
    PRODUCT_OFFSALE_SUCCESS(26,"商品下架成功"),
    CATEGORY_SAVE_SUCCESS(27,"类目修改成功"),
    CATEGORY_ADD_SUCCESS(28,"类目新增成功"),
    CATEGORY_EXIST(29,"类目已存在")



    ;


    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
