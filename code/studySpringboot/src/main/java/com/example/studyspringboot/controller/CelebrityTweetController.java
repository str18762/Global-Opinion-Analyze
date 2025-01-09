package com.example.studyspringboot.controller;


import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.CelebrityTweet;
import com.example.studyspringboot.service.CelebrityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/celebrity-tweets")
public class CelebrityTweetController {

    private final CelebrityService celebrityService;

    /**
     * 获取所有数据
     * @return  Result
     */
    @GetMapping("/all")
    public Result getALl(){
        List<CelebrityTweet> celebrityTweets = celebrityService.selectAll();
        return Result.success(celebrityTweets);
    }

    /**
     * 获取指定作者的数据
     * @param author 作者
     * @return  Result
     */
    @GetMapping("/author")
    public Result getByAuthor(String author){
        List<CelebrityTweet> celebrityTweets = celebrityService.selectByAuthor(author);
        return Result.success(celebrityTweets);
    }
}
