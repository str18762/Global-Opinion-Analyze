package com.example.studyspringboot.mapper;


import com.example.studyspringboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into `user_tb`(username,password,register_time,focus_num,role) " +
            "values(#{username},#{password},#{register_time},#{focus_num},#{role})")
    void insert(User user);

    @Delete("delete from `user_tb` where id=#{id}")
    void delete(@Param("id") Integer id);

    @Update("update `user_tb` set username=#{username},password=#{password},avatar=#{avatar},name=#{name},description=#{description} " +
            "where id=#{id}")
    void update(User user);

    @Select("select * from `user_tb`")
    List<User> selectAll();

    @Select("select * from `user_tb` where id=#{id}")
    User selectById(Integer id);

    @Select("select * from `user_tb` where username=#{username}")
    User selectByUsername(String username);

    @Update("update `user_tb` set focus_num=focus_num+1 where username=#{username}")
    void collect_chara(String username);

    @Update("update `user_tb` set focus_num=focus_num-1 where username=#{username}")
    void cancleCollect_chara(String username);

    @Select("select focus_num from `user_tb` where id=#{id}")
    int getCollectNum(int id);
}
