package com.example.eshopms.controller;

import com.example.eshopms.entity.Order;
import com.example.eshopms.entity.OrderDetail;
import com.example.eshopms.entity.User;
import com.example.eshopms.service.OrderDetailService;
import com.example.eshopms.service.OrderService;
import com.example.eshopms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminOrderController {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @RequestMapping(value = "/addorder",method = RequestMethod.GET)
    public int addOrder(int userid,String orderDetailList){
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

    @RequestMapping(value = "/deleteorder",method = RequestMethod.GET)
    public int deleteOrder(int id){
        List<OrderDetail> orderDetailList=orderDetailService.getOrderDetailListByOrderId(id);
        boolean b=true;
        for(OrderDetail i:orderDetailList){
            b= orderDetailService.deleteOrderDetail(i.getId());
            if(!b) return 0;
        }
        boolean b1= orderService.deleteOrder(id);
        return b1?1:0;
    }

    @RequestMapping(value = "/updateorder",method = RequestMethod.GET)
    public int updateOrder(int id,int userid,String time,String orderDetailList){
        List<User> userList=userService.getUserList();
        if(userList.isEmpty()) return -2;
        boolean userIdFlag=false;
        for(User i:userList){
            if(i.getId()==userid) {
                userIdFlag = true;
                break;
            }
        }
        if(!userIdFlag) return -1;
        Order order= orderService.getOrderById(id);
        order.setUserId(userid);
        order.setTime(LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        List<OrderDetail> orderDetailList1=orderDetailService.getOrderDetailListByOrderId(id);
        boolean b1=true;
        for(OrderDetail i:orderDetailList1){
            b1=orderDetailService.deleteOrderDetail(i.getId());
            if(!b1) return 0;
        }
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
        boolean b=orderService.modifyOrder(order);
        if(!b) return 0;
        for(OrderDetail i:orderDetails) {
            i.setOrderId(order.getId());
            boolean c = orderDetailService.addOrderDetail(i);
            if (!c) return 0;
        }
        return 1;
    }

    @RequestMapping(value = "/getorderlist",method = RequestMethod.GET)
    public List<Order> getOrderList(){
        return orderService.getOrderList();
    }
}
