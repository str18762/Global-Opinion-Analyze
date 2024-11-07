package com.example.studyspringboot.mapper;

import com.example.studyspringboot.entity.Emotion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmotionMapper {

    @Select("select * from `emotion` where name=#{name} and month=#{month}")
    Emotion selectMonth(@Param("name") String name,@Param("month") String month);

    @Select("select * from `emotion` where name=#{name} and year=#{year}")
    List<Emotion> selectByYear(@Param("name") String name,@Param("year") String year);
}