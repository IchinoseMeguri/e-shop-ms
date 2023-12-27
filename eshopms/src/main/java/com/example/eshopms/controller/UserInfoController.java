package com.example.eshopms.controller;

import com.example.eshopms.entity.User;
import com.example.eshopms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserInfoController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/getuser",method = RequestMethod.GET)
    public User getUserByName(String name){
        List<User> list=userService.selectUserByName(name);
        if(list.size()==0) return null;
        else{
            return list.get(0);
        }
    }

    @RequestMapping(value = "/changepwd",method = RequestMethod.GET)
    public int changePwd(int id,String pwd,String newpwd){
        User user=userService.selectUserById(id);
        if(!user.getPwd().equals(pwd)) return -1;
        else{
            user.setPwd(newpwd);
            boolean b=userService.modifyUser(user);
            return b?1:0;
        }
    }

    @RequestMapping(value = "/updateuserinfo",method = RequestMethod.GET)
    public int updateUserInfo(int id,String phone,String address){
        User user=userService.selectUserById(id);
        user.setPhone(phone);
        user.setAddress(address);
        boolean b=userService.modifyUser(user);
        return b?1:0;
    }
}
