package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.Book;
import com.example.demo.pojo.Result;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @PostMapping
    public Result save(@RequestBody Book book){
        bookService.save(book);
        return Result.success();
    }
    @PutMapping//更新
    public Result update(@RequestBody Book book){
        bookService.updateById(book);
        return Result.success();
    }
    @DeleteMapping("/{id}")//更新
    public Result delete(@PathVariable Long id){
        bookService.removeById(id);
        return Result.success();
    }
    @GetMapping//查询
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam(defaultValue = "") String search){
        return Result.success(bookService.selectBookPage(pageNum,pageSize,search));
    }
}
