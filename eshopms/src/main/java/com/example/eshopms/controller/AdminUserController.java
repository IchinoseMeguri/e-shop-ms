package com.example.eshopms.controller;

import com.example.eshopms.entity.User;
import com.example.eshopms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/updateuserinfo",method = RequestMethod.GET)
    public int updateUserInfo(int id,String type,String name,String phone,String address){
        List<User> userList=userService.selectUserByName(name);
        if(userList.size()>0)
            if(userList.get(0).getId()!=id) return -1;
        User user=userService.selectUserById(id);
        user.setType(type);
        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);
        boolean b= userService.modifyUser(user);
        return b?1:0;
    }

    @RequestMapping(value = "/resetpwd",method = RequestMethod.GET)
    public int resetPwd(int id){
        User user=userService.selectUserById(id);
        user.setPwd("123456");
        boolean b= userService.modifyUser(user);
        return b?1:0;
    }

    @RequestMapping(value = "/deleteuser",method = RequestMethod.GET)
    public int deleteUser(int id){
        boolean b=userService.deleteUser(id);
        return b?1:0;
    }

    @RequestMapping(value = "/adduser",method = RequestMethod.GET)
    public int addUser(String type,String name,String phone,String address){
        List<User> userList=userService.selectUserByName(name);
        if(userList.size()>0) return -1;
        User user=new User();
        user.setType(type);
        user.setName(name);
        user.setPwd("123456");
        user.setPhone(phone);
        user.setAddress(address);
        boolean b=userService.addUser(user);
        return b?1:0;
    }

    @RequestMapping(value = "/getuserlist",method = RequestMethod.GET)
    public List<User> getUserList(){
        return userService.getUserList();
    }
}
