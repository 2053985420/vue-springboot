package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.mapper.UserMappper;
import com.example.demo.pojo.Book;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    UserService userService;
    @Test
    void selectUserPage() {
        userService.selectUserPage(1,5,"");
    }
    @Autowired
    UserController userController;
    @Test
    void deleteUser(){
        userController.deleteUser(Long.valueOf(2));
    }
    @Autowired
    UserMappper userMappper;
    @Test
    void selectBookByUser(){
        List<Book> books=userMappper.selectBookByUser(Long.valueOf(2));
        for(Book book:books){
            System.out.println(book);
        }
    }

}
