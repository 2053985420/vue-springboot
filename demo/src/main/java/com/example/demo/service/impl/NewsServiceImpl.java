package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.NewsMapper;
import com.example.demo.pojo.News;
import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public Page<News> selectNewsPage(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<News> wrapper = Wrappers.lambdaQuery();
        if(search!=null&&!search.equals("")){
            wrapper.like(News::getTitle,search);
        }
        return newsMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
    }
}
