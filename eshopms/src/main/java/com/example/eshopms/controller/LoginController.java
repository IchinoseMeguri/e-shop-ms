package com.example.eshopms.controller;

import com.example.eshopms.entity.User;
import com.example.eshopms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public int login(String name, String pwd){
        List<User> userList=userService.selectUserByName(name);
        if(userList.size()==0){
            System.out.println("login failed");
            return 0;
        }
        User user=userList.get(0);
        if (pwd.equals(user.getPwd())) {
            System.out.println("login success");
            return 1;
        }
        else {
            System.out.println("login failed");
            return 0;
        }
    }

    @RequestMapping("/home")
    public String home(){
        return "登录";
    }
}
