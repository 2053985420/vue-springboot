package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.BookMapper;
import com.example.demo.pojo.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public Page<Book> selectBookPage(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        if(search!=null&&!search.equals("")){
            wrapper.like(Book::getName,search);
        }
        return bookMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
    }
}
