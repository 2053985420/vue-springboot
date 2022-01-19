package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.Book;

public interface BookService extends IService<Book> {
    Page<Book> selectBookPage(Integer pageNum, Integer pageSize);

    Object getOneBook(String key);
    void updateBook(Book book);
    void deleteBook(Long id);
}
