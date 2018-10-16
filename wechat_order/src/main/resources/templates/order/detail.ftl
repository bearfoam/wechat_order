<html>
<#include "../common/header.ftl">
<body>
    <div id="wrapper" class="toggled">
    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
     <div id="page-content-wrapper">
         <div class="container">
             <div class="row clearfix">
                 <div class="col-md-5 column">
                     <table class="table table-bordered">
                         <thead>
                         <tr>
                             <th>订单ID</th>
                             <th>订单金额</th>
                         </tr>
                         </thead>
                         <tbody>
                         <tr>
                             <td>${orderDTO.getOrderId()}</td>
                             <td>${orderDTO.getOrderAmount()}</td>
                         </tr>
                         </tbody>
                     </table>
                 </div>

             <#--订单详情表 -->
                 <div class="col-md-12 column">
                     <table class="table table-bordered">
                         <thead>
                         <tr>
                             <th>商品ID</th>
                             <th>商品名称</th>
                             <th>价格</th>
                             <th>数量</th>
                             <th>总额</th>
                         </tr>
                         </thead>
                         <tbody>
                         <tr>
                        <#list orderDTO.orderDetailList as orderDetail>
                            <td>${orderDetail.getOrderId()}</td>
                            <td>${orderDetail.getProductName()}</td>
                            <td>${orderDetail.getProductPrice()}</td>
                            <td>${orderDetail.productQuantity}</td>
                            <td>${orderDetail.productQuantity * orderDetail.getProductPrice()}</td>
                        </#list>
                         </tr>
                         </tbody>
                     </table>
                 </div>

                 <div class="col-md-12 column">
                <#if orderDTO.getOrderStatusEnum().getMessage() == "新订单">
                    <a href="/sell/seller/order/finish?orderId=${orderDTO.getOrderId()}" type="button" class="btn btn-default btn-success">完结订单</a>
                    <a href="/sell/seller/order/cancel?orderId=${orderDTO.getOrderId()}" type="button" class="btn btn-default btn-danger">取消订单</a>
                </#if>
                 </div>
             </div>
         </div>
     </div>
    </div>

</body>
</html>