package com.example.studyspringboot.mapper;


import com.example.studyspringboot.entity.News;
import com.example.studyspringboot.entity.News_bbc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsMapper {

    @Select("SELECT * from `all_news`")
    List<News> getAllNews();

    @Select("SELECT * from `all_news` where id=#{id}")
    News selectNewsById(@Param("id") int id);

    @Select("SELECT * from `bbc_news` where id=#{id}")
    News_bbc bbcNewsById(@Param("id") int id);

    @Select("SELECT * from `bbc_news`")
    List<News_bbc> getAllBBCNews();

    @Select("SELECT * from `bbc_news` where relativity=#{relativity}")
    List<News_bbc> selectBBCNewsByRelativity(String relativity);
}
