package com.ly.repository;

import com.ly.dataproject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository <OrderMaster,String>{//后面的参数表示主键的类型
    /** 根据买家微信查出该买家的所有订单*/
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
