package com.example.eshopms.service;

import com.example.eshopms.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderService {
    public List<Order> getOrderList();
    public Order getOrderById(int id);
    public List<Order> getOrderListByUser(int user);
    public boolean addOrder(Order order);
    public boolean deleteOrder(int id);
    public boolean modifyOrder(Order order);
}
