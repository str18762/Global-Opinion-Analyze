package com.example.studyspringboot.controller;

import com.example.studyspringboot.common.AuthAccess;
import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.News;
import com.example.studyspringboot.entity.News_bbc;
import com.example.studyspringboot.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/all")
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @AuthAccess
    @GetMapping("/selectById")
    public Result selectNewsById(int id) {
        News news;
        news = newsService.selectNewsById(id);
        return Result.success(news);
    }

    @AuthAccess
    @GetMapping("/getAllBBCNews")
    public Result getAllBBCNews() {
        List<News_bbc> news_bbc;
        news_bbc = newsService.getAllBBCNews();
        return Result.success(news_bbc);
    }

    @AuthAccess
    @GetMapping("/bbcNewsById")
    public Result bbcNewsById(int id) {
        News_bbc news_bbc;
        news_bbc = newsService.bbcNewsById(id);
        return Result.success(news_bbc);
    }

    @AuthAccess
    @GetMapping("/selectBBCNewsByRelativity")
    public Result selectBBCNewsByRelativity(String relativity) {
        List<News_bbc> news_bbc;
        news_bbc = newsService.selectBBCNewsByRelativity(relativity);
        return Result.success(news_bbc);
    }
}
