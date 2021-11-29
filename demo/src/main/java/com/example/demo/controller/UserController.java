package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User queryUser =userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()));
        if(queryUser==null||!queryUser.getPassword().equals(user.getPassword())){
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success(queryUser);
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        User queryUser =userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()));
        if(queryUser!=null){
            return Result.error("-1","用户名重复");
        }
        userService.save(user);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Long id){
        return Result.success(userService.getById(id));
    }
    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam(defaultValue = "") String search){

        return Result.success(userService.selectUserPage(pageNum,pageSize,search));
    }
    @DeleteMapping
    public Result deleteUser(@RequestParam Long id){
        System.out.println(id);
        userService.removeById(id);
        return Result.success();
    }
    @PutMapping
    public Result updateUser(@RequestBody User user){
        User queryUser =userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()));
        if(queryUser.getId()!=user.getId()){
            return Result.error("-1","用户名已存在");
        }
        userService.updateById(user);
        return Result.success();
    }
    @PostMapping
    public Result save(@RequestBody User user){
        User queryUser =userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()));
        if(queryUser!=null){
            return Result.error("-1","用户名已存在");
        }
        else{
            userService.save(user);
            return Result.success();
        }
    }
}
