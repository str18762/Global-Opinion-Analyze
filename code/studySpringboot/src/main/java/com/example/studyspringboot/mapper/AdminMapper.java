package com.example.studyspringboot.mapper;

import com.example.studyspringboot.entity.*;
import com.example.studyspringboot.entity.Character;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("select * from `user_tb` where role=#{role}")
    List<User> listAllAdmin(String role);

    @Select("select  * from `user_tb` where role=#{role}")
    List<User> listAllUser(@Param("userName") String userName, @Param("role")String role);

    @Delete("delete from user_tb where id=#{id}")
    void delete(Integer id);

    @Update("update user_tb set password=#{password} where id=#{id}")
    void resetPassword(@Param("id") Integer id, @Param("password") String password);

    @Delete("delete from characters where id=#{id}")
    void deleteInfluence(Integer id);

    @Update("update characters set username=#{username},name=#{name}," +
            "introduction=#{introduction} where photo=#{photo}")
    void updateInfluencePerson(Character character);

    @Select("select * from all_news")
    List<News_old> listAllNews();

    @Select("select * from news where what like concat('%',#{what},'%')")
    List<News_old> listNewsByWhat(String what);

    @Delete("delete from news where `where`=#{where}")
    void deleteNew(String where);

    @Select("select * from emotion")
    List<EmotionVO> listAllEmotion();

    @Select("select * from emotion where detailEmotion like concat('%',#{detail},'%')" +
            "and name like concat('%',#{name},'%')")
    List<EmotionVO> listEmotionByDetail(@Param("detail") String detail,@Param("name")String name);

    @Delete("delete from emotion where name=#{name} and year=#{year} and month=#{month}")
    void deleteEmotion(EmotionVO emotionVO);

    @Select("select * from emotion")
    List<EmotionExtraVO> listAllExtraEmotion();

    @Select("select * from emotion where generalEmotion like concat('%',#{general},'%')" +
            "and name like concat('%',#{name},'%')")
    List<EmotionExtraVO> listExtraEmotionByDetail(@Param("general") String general,@Param("name") String name);

    @Delete("delete from emotion where name=#{name} and year=#{year} and month=#{month}")
    void deleteExtraEmotion(EmotionExtraVO emotionExtraVO);
}
