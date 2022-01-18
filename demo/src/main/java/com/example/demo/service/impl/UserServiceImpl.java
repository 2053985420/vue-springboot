package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.UserMappper;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMappper, User> implements UserService {
    @Autowired UserMappper userMappper;
    @Autowired
    RedisTemplate redisTemplate;
    public Page<User> selectUserPage(Integer pageNum,Integer pageSize, String search) {
//        if(search!=null&&!search.equals("")){
//            String key = "user:"+
//        }
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(search!=null&&!search.equals("")){
            wrapper.like(User::getNickName,search);
        }
        return userMappper.selectPage(new Page<>(pageNum,pageSize),wrapper);
    }
}
