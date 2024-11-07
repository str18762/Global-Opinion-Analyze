package com.example.studyspringboot.mapper;


import com.example.studyspringboot.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Insert("insert into `student`(sno,sname) " +
            "values(#{sno},#{sname})")
    void insert(Student student);

    @Delete("delete from `student` where sno=#{sno}")
    void delete(@Param("sno") String sno);

    @Update("update `student` set ssex=#{ssex},sage=#{sage},sdept=#{sdept} " +
            "where sno=#{sno}")
    void update(Student student);

    @Select("select * from `student`")
    List<Student> selectAll();

    @Select("select * from `student` where sno=#{sno}")
    Student selectBySno(String sno);

    @Select("select * from `student` where sname=#{sname}")
    Student selectBySname(String sname);

    @Select("SELECT s.* FROM student s JOIN sc ON s.sno = sc.sno WHERE sc.cno = #{cno}")
    List<Student> findStudentsByCourseNumber(@Param("cno") Integer cno);
}
