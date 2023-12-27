package com.example.eshopms.service;

import com.example.eshopms.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    public List<User> getUserList();
    public User selectUserById(int id);
    public List<User> selectUserByName(String name);
    public boolean addUser(User user);
    public boolean deleteUser(int id);
    public boolean modifyUser(User user);
}
