package com.example.eshopms.service.impl;

import com.example.eshopms.entity.User;
import com.example.eshopms.dao.UserDao;
import com.example.eshopms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public User selectUserById(int id) {
        return userDao.selectUserById(id);
    }

    @Override
    public List<User> selectUserByName(String name) {
        return userDao.selectUserByName(name);
    }

    @Override
    public boolean addUser(User user) {
        int result= userDao.addUser(user);
        return result>0;
    }

    @Override
    public boolean deleteUser(int id) {
        int result= userDao.deleteUser(id);
        return result>0;
    }

    @Override
    public boolean modifyUser(User user) {
        int result= userDao.modifyUser(user);
        return result>0;
    }
}
