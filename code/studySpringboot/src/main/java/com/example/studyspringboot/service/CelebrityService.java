package com.example.studyspringboot.service;

import com.example.studyspringboot.entity.CelebrityTweet;
import com.example.studyspringboot.mapper.CelebrityTweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CelebrityService {

    private final CelebrityTweetMapper celebrityTweetMapper;

    /**
     * 查询所有推文数据
     * @return 推文数据列表
     */
    public List<CelebrityTweet> selectAll() {
        return celebrityTweetMapper.selectAll();
    }

    /**
     * 查询指定作者的推文数据
     * @param author 作者姓名
     * @return  推文数据列表
     */
    public List<CelebrityTweet> selectByAuthor(String author) {
        return celebrityTweetMapper.selectByAuthor(author);
    }

}
