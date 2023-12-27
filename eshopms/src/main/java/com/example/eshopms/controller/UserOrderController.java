package com.example.eshopms.controller;

import com.example.eshopms.entity.Order;
import com.example.eshopms.entity.OrderDetail;
import com.example.eshopms.service.OrderDetailService;
import com.example.eshopms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserOrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;

    @RequestMapping(value = "/createorder",method = RequestMethod.GET)
    public int createOrder(int userid,String orderDetailList){
        Order order=new Order();
        order.setUserId(userid);
        order.setTime(LocalDateTime.now());
        float price=0;
        String[] str=orderDetailList.split("&");
        float[] n=new float[str.length];
        for(int i=0;i<str.length;i++) n[i]=Float.parseFloat(str[i]);
        List<OrderDetail> orderDetails=new ArrayList<OrderDetail>();
        for (int i=0;i<n.length;) {
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setCommodityId(Integer.parseInt(str[i]));
            i++;
            orderDetail.setQuantity(Integer.parseInt(str[i]));
            i++;
            orderDetail.setPrice(n[i]);
            price+=n[i];
            i++;
            orderDetails.add(orderDetail);
        }
        order.setPrice(price);
        boolean b=orderService.addOrder(order);
        if(!b) return 0;
        else{
            List<Order> orderList=orderService.getOrderListByUser(userid);
            if(orderList.size()==0) return 0;
            else{
                int orderId=orderList.get(orderList.size()-1).getId();
                for(OrderDetail i:orderDetails){
                    i.setOrderId(orderId);
                    boolean c=orderDetailService.addOrderDetail(i);
                    if(!c) return 0;
                }
                return 1;
            }
        }
    }

    @RequestMapping(value = "/getuserorder",method = RequestMethod.GET)
    public List<Order> getUserOrder(int userid){
        return orderService.getOrderListByUser(userid);
    }

    @RequestMapping(value = "/getorderdetail",method = RequestMethod.GET)
    public List<OrderDetail> getOrderDetail(int orderid){
        return orderDetailService.getOrderDetailListByOrderId(orderid);
    }

}
