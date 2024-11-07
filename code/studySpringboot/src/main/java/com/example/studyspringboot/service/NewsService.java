package com.example.studyspringboot.service;


import com.example.studyspringboot.entity.News;
import com.example.studyspringboot.entity.News_bbc;
import com.example.studyspringboot.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsMapper newsMapper;

    public List<News> getAllNews() {
        return newsMapper.getAllNews();
    }

    public News selectNewsById(int id) {
        return newsMapper.selectNewsById(id);
    }

    public News_bbc bbcNewsById(int id) {
        return newsMapper.bbcNewsById(id);
    }

    public List<News_bbc> getAllBBCNews() {
        return newsMapper.getAllBBCNews();
    }
}
