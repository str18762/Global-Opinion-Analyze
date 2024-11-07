/**
 * 功能：
 * 作者：
 * 日期：2024/7/18 下午9:17
 */
package com.example.studyspringboot.service;

import com.example.studyspringboot.entity.*;
import com.example.studyspringboot.entity.Character;
import com.example.studyspringboot.mapper.CharacterMapper;
import com.example.studyspringboot.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CharacterService {

    @Autowired
    CharacterMapper characterMapper;
    @Autowired
    UserMapper userMapper;

    public List<Character> selectAll(){
        return characterMapper.selectAll();
    }

    public List<CharaTweet> selectPresidentTweets() {
        return characterMapper.selectPresidentTweets();
    }

    public List<CharaTweet> selectPoliticianTweets() {
        return characterMapper.selectPoliticianTweets();
    }

    public List<CharaTweet> selectDiplomatTweets() {
        return characterMapper.selectDiplomatTweets();
    }

    public List<CharaTweet> selectEconomistTweets() {
        return characterMapper.selectEconomistTweets();
    }
    public DataVO getFansNum() {
        return generalNum(500, 1000);
    }

    public DataVO getCommentNum() {
        return generalNum(1000, 10000);
    }

    public DataVO generalNum(int minBound, int maxBound){
        Random r = new Random();
        int bound = r.nextInt(maxBound - minBound + 1) + minBound;

        LocalDate end = LocalDate.now();
        LocalDate begin = end.minusMonths(12);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");

        List<String> dateList = new ArrayList<>();
        List<Integer> fansList = new ArrayList<>();

        while (!begin.isAfter(end)){
            dateList.add(dtf.format(begin));
            fansList.add(r.nextInt(bound));
            begin = begin.plusMonths(1);
        }

        return DataVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .dataList(StringUtils.join(fansList, ","))
                .build();
    }

    public InfluenceVO getPersonInfluence(String name) {
        Random r = new Random();
        int up = r.nextInt(1000);
        int relay = r.nextInt(500);
        int comment = r.nextInt(2000);
        int look = r.nextInt(50000);
        int follow = r.nextInt(10000);
        int follower = r.nextInt(100000);

        String mediaExposure,socialMediaInfluence,publicOpinion;
        int index1 = look+comment;
        double index2 = (follower*0.4+up*0.15+comment*0.2+relay*0.2+look*0.05)/
                (up+relay+comment+look+follower+follow);
        int index3 = up+relay;
        int totalInfluence = 0;
        if(index1>10000){
            mediaExposure = "高";
            totalInfluence+=r.nextInt(6)+25;
        } else if (index1<3000) {
            mediaExposure = "低";
            totalInfluence+=r.nextInt(7)+4;
        }else {
            mediaExposure = "中";
            totalInfluence+=r.nextInt(7)+14;
        }
        if(index2>0.5){
            socialMediaInfluence = "高";
            totalInfluence+=r.nextInt(4)+27;
        } else if (index2<0.1) {
            socialMediaInfluence = "低";
            totalInfluence+=r.nextInt(5)+6;
        }else {
            socialMediaInfluence = "中";
            totalInfluence+=r.nextInt(6)+15;
        }
        if(index3>500){
            publicOpinion = "好";
            totalInfluence+=r.nextInt(7)+24;
        } else if (index3<80) {
            publicOpinion = "差";
            totalInfluence+=r.nextInt(8)+3;
        }else {
            publicOpinion = "中";
            totalInfluence+=r.nextInt(7)+14;
        }
        return InfluenceVO.builder()
                .mediaExposure(mediaExposure)
                .socialMediaInfluence(socialMediaInfluence)
                .publicOpinion(publicOpinion)
                .totalInfluence(totalInfluence)
                .build();

    }

    public Character selectCharaByName(String name,String username) {
        return characterMapper.selectCharaByName(name,username);
    }

    public List<Character> selectByField(String field) {
        return characterMapper.selectByField(field);
    }

    public List<Character> selectAllChara(String username){
        return characterMapper.selectAllChara(username);
    }

    public int addCollect(String name, String username) {
        LocalDate today = LocalDate.now();
        // 格式化日期为 YYYY-MM-DD 格式
        String focus_time = today.toString(); // 或者使用 DateTimeFormatter 进行自定义格式化
        userMapper.collect_chara(username);
        return characterMapper.addCollect(name,username,focus_time);
    }

    public int deleteCollect(String name, String username) {
        userMapper.cancleCollect_chara(username);
        return characterMapper.deleteCollect(name,username);
    }

    public List<Character> selectCollectChara(String username) {
        return characterMapper.selectCollectChara(username);
    }

    public List<Character> selectCharaByEmotion(String emotion) {
        return characterMapper.selectCharaByEmotion(emotion);
    }
}
