package com.example.demo.controller;

import com.example.demo.pojo.News;
import com.example.demo.pojo.Result;
import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController //用于标注控制层组件(如struts中的action)，@ResponseBody和@Controller的合集。
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    @PostMapping//新增
    public Result save(@RequestBody News news){
        news.setTime(new Date());
        newsService.save(news);
        return Result.success();
    }
    @PutMapping//更新
    public Result update(@RequestBody News news){
        newsService.updateById(news);
        return Result.success();
    }
    @DeleteMapping("/{id}")//更新
    public Result delete(@PathVariable Long id){
        newsService.removeById(id);
        return Result.success();
    }
    @GetMapping//查询
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam(defaultValue = "") String search){
        return Result.success(newsService.selectNewsPage(pageNum,pageSize,search));
    }
}
