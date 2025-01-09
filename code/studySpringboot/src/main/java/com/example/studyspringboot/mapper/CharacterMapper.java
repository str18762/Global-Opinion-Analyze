package com.example.studyspringboot.mapper;


import com.example.studyspringboot.entity.CharaTweet;
import com.example.studyspringboot.entity.Character;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CharacterMapper {

    @Select("select * from `characters` ")
    List<Character> selectAll();

    @Select("select * from `presidents_tweets`")
    List<CharaTweet> selectPresidentTweets();

    @Select("select * from `politician_tweets`")
    List<CharaTweet> selectPoliticianTweets();

    @Select("select * from `diplomats_tweets`")
    List<CharaTweet> selectDiplomatTweets();

    @Select("select * from `economisttweets`")
    List<CharaTweet> selectEconomistTweets();

    @Select("select * from `characters` where field=#{field}")
    List<Character> selectByField(@Param("field") String field);


    //带返回是否该用户关注了该人物属性的返回
    String selectSql="select s.*,(case when l.username is not null then 1 else 0 end) collect from celebrity s left join (select * from chara_like where username=#{username}) l on s.name=l.name";

    @Select(selectSql)
    List<Character> selectAllChara(@Param("username") String username);

    @Select(selectSql+" where s.name=#{name}")
    Character selectCharaByName(@Param("name") String name,@Param("username") String username);

    @Insert("insert into chara_like(name,username,focus_time) values(#{name},#{username},#{focus_time})")
    int addCollect(@Param("name") String name,@Param("username") String username,@Param("focus_time") String focus_time);

    @Delete("delete from `chara_like` where name=#{name} and username=#{username}")
    int deleteCollect(@Param("name") String name,@Param("username") String username);

    @Select(selectSql+" where l.username=#{username}")
    List<Character> selectCollectChara(String username);

    @Select("select a.* from characters a,tochinaemotion b where a.name=b.who and b.emotion_analysis_result=#{emotion}")
    List<Character> selectCharaByEmotion(@Param("emotion") String emotion);


}
