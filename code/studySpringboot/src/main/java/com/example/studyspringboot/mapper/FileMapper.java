package com.example.studyspringboot.mapper;

import com.example.studyspringboot.entity.SnapshotVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("insert into snapshot(username,url,date) values (#{username},#{url},#{date})")
    void saveSnapshot(@Param("username") String username,@Param("url") String url,@Param("date") LocalDate date);

    @Select("select * from snapshot where username=#{username}")
    List<SnapshotVO> selectAllSnapshot(String username);
}
