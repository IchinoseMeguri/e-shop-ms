package com.example.eshopms.service;

import com.example.eshopms.entity.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderDetailService {
    public List<OrderDetail> getOrderDetailList();
    public List<OrderDetail> getOrderDetailListByOrderId(int id);
    public List<OrderDetail> getOrderDetailListByCommodityId(int id);
    public OrderDetail getOrderDetailById(int id);
    public boolean addOrderDetail(OrderDetail orderDetail);
    public boolean deleteOrderDetail(int id);
    public boolean modifyOrderDetail(OrderDetail orderDetail);
}
