package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.Book;
import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMappper extends BaseMapper<User> {
    List<Book> selectBookByUser(@Param("UserId") Long id);
}
