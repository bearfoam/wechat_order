package com.ly.service.impl;

import com.ly.converter.ordermaster_orderDTO;
import com.ly.dataproject.OrderDetail;
import com.ly.dataproject.OrderMaster;
import com.ly.dataproject.ProductInfo;
import com.ly.dto.CartDTO;
import com.ly.dto.OrderDTO;
import com.ly.enums.OrderStatusEnum;
import com.ly.enums.PayStatusEnum;
import com.ly.enums.ResultEnum;
import com.ly.exception.SellException;
import com.ly.repository.OrderDetailRepository;
import com.ly.repository.OrderMasterRepository;
import com.ly.service.OrderService;
import com.ly.service.PayService;
import com.ly.service.ProductService;
import com.ly.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ly
 * @ 2018-09-13
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private  ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    private PayService payService;



    BigDecimal orderAmount = new BigDecimal(0);

    String orderid =KeyUtil.getUniqueKey();

    /** 创建订单*/
    @Override
    @Transactional  //添加事务注解一旦库存不够就会抛出异常，然后事务回滚（当你使用事务时，一旦抛出异常，事务就会回滚）
    public OrderDTO create(OrderDTO orderDTO) {
        //1.查询商品数量
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        for(OrderDetail orderDetail : orderDetailList){
            ProductInfo productInfo =productService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算订单总价 BigDecimal的乘不一样
            orderAmount =  productInfo.getProductPrice().multiply(new BigDecimal( orderDetail.getProductQuantity())).add(orderAmount);

            //订单详情记入数据库

            orderDetail.setOrderId(orderid);//订单id
            orderDetail.setDetailId(KeyUtil.getUniqueKey()); //主键
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        //3.订单记入数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderid);
        BeanUtils.copyProperties(orderDTO,orderMaster);//拷贝属性的时候会覆盖orderAmount，OrderStatus，PayStatus，所以需要重新赋值
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMasterRepository.save(orderMaster);

        //4.扣库存 使用lamda表达式获得购物车list
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    /**查询单个订单详情*/
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster =  orderMasterRepository.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
       List<OrderDetail> orderDetailList =  orderDetailRepository.findByOrderId(orderId);
        if(orderDetailList.size()==0){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    /**查询订单列表*/
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
       Page<OrderMaster> orderMasterPage =  orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList =  ordermaster_orderDTO.converter(orderMasterPage.getContent());
       Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }
    /**取消订单*/
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态(只有为新订单才可以取消)
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】,订单状态不正确,orderId{},orderStatus{}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);

        if(updateResult == null){
            log.error("【取消订单】,取消订单失败，orderMaster{}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存,参数为List<CartDTO> cartDTOList
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //如果用户付款，给用户退款
        if(orderMaster.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            payService.refund(orderDTO);
        }

        return orderDTO;
    }

    @Override
    /**完结订单*/
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        OrderMaster orderMaster = new OrderMaster();
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】,订单状态不正确,orderId{},orderStatus{}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【完结订单】,更新失败，orderMaster{}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    /**支付订单*/
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单支付完成】,订单状态不正确,orderId{},orderStatus{}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付完成】,订单支付状态不正确,orderDTO{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAID_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【订单支付完成】,更新失败，orderMaster{}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }
}
