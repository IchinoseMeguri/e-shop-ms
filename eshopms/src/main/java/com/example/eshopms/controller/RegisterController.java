package com.example.eshopms.controller;

import com.example.eshopms.entity.User;
import com.example.eshopms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegisterController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public int Register(String name,String pwd,String phone,String address){
        List<User> list=userService.selectUserByName(name);
        if (list.size()>0) {
            System.out.println("repeated name");
            return -1;
        }
        User user=new User();
        user.setType("用户");
        user.setName(name);
        user.setPwd(pwd);
        user.setPhone(phone);
        user.setAddress(address);
        boolean b=userService.addUser(user);
        if (b) {
            System.out.println("register success");
            return 1;
        }
        else {
            System.out.println("register failed");
            return 0;
        }
    }

}
