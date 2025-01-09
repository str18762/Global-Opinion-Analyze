package com.example.studyspringboot.mapper;

import com.example.studyspringboot.entity.CelebrityTweet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CelebrityTweetMapper {

    @Select("SELECT * FROM celebrity_tweets ORDER BY time DESC")
    List<CelebrityTweet> selectAll();

    @Select("SELECT * FROM celebrity_tweets WHERE author = #{author} ORDER BY time DESC")
    List<CelebrityTweet> selectByAuthor(String author);
}
