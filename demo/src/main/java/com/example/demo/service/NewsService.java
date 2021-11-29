package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.News;

public interface NewsService extends IService<News> {
    Page<News> selectNewsPage(Integer pageNum, Integer pageSize, String search);

}
