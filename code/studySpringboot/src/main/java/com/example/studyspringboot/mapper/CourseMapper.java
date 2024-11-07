package com.example.studyspringboot.mapper;


import com.example.studyspringboot.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Insert("insert into `course`(cno,cname,cpno,credit) " +
            "values(#{cno},#{cname},#{cpno},#{credit})")
    void insert(Course course);

    @Delete("delete from `course` where cno=#{cno}")
    void delete(@Param("cno") Integer cno);

    @Update("update `course` set cname=#{cname},cpno=#{cpno},credit=#{credit} " +
            "where cno=#{cno}")
    void update(Course course);

    @Select("select * from `course`")
    List<Course> selectAll();

    @Select("select * from `course` where cno=#{cno}")
    Course selectByCno(Integer cno);

    @Select("select * from `course` where cname=#{cname}")
    Course selectByCname(String cname);
}
