package com.example.eshopms.dao;

import com.example.eshopms.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    @Select("select * from user")
    public List<User> getUserList();
    @Select("select * from user where id=#{id}")
    public User selectUserById(int id);
    @Select("select * from user where name=#{name}")
    public List<User> selectUserByName(String name);
    @Insert("insert into user(type,name,pwd,phone,address) values(#{type},#{name},#{pwd},#{phone},#{address})")
    public int addUser(User user);
    @Delete("delete from user where id=#{id}")
    public int deleteUser(int id);
    @Update("update user set type=#{type},name=#{name},pwd=#{pwd},phone=#{phone},address=#{address} where id=#{id}")
    public int modifyUser(User user);
}
