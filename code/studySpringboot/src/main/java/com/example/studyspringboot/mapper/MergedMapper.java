package com.example.studyspringboot.mapper;

import com.example.studyspringboot.entity.Emotion;
import com.example.studyspringboot.entity.Merged;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MergedMapper {

    @Select("SELECT * FROM merged")
    List<Merged> selectAll();

    @Select("SELECT * FROM merged WHERE id=#{id}")
    Merged selectById(@Param("id") Integer id);

    @Select("SELECT * FROM merged WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Merged> selectByName(@Param("name") String name);
}