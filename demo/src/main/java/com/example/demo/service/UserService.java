package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.User;

public interface UserService extends IService<User> {
    Page<User> selectUserPage(Integer pageNum,Integer pageSize, String search);
}
