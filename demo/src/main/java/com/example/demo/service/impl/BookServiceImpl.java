package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.BookMapper;
import com.example.demo.pojo.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public Page<Book> selectBookPage(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        return bookMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
    }

    @Override
    public Object getOneBook(String key) {
        Object book =  redisTemplate.opsForValue().get(key);
        if(book==null){
            synchronized (this.getClass()){
                book =  redisTemplate.opsForValue().get(key);
                if(book==null){
                    System.out.println("-------------查询数据库--------");
                    LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
                    wrapper.eq(Book::getName,key);
                    Book result = bookMapper.selectOne(wrapper);
                    redisTemplate.opsForValue().set(key,result);
                    return result;
                } else{
                    return book;
                }
            }

        } else{
            return book;
        }
    }

    @Override
    public void updateBook(Book book) {
        bookMapper.updateById(book);
        redisTemplate.opsForValue().set(book.getName(),book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookMapper.selectById(id);
        bookMapper.deleteById(id);
        redisTemplate.delete(book.getName());
    }
}
