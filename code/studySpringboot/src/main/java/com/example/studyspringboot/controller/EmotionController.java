/**
 * 功能：
 * 作者：
 * 日期：2024/8/24 上午12:10
 */
package com.example.studyspringboot.controller;

import com.example.studyspringboot.common.AuthAccess;
import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.Emotion;
import com.example.studyspringboot.service.EmotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@RestController
@RequestMapping("/emotion")
public class EmotionController {

    @Autowired
    private EmotionService emotionService;

    //需要人物名字name
    @AuthAccess
    @GetMapping("/selectLastMonth")
    public Result selectLastMonth(String name) {
        Emotion emotion;
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 获取当前月份
        Month currentMonth = today.getMonth();
        String monthNumber = String.valueOf(today.getMonthValue());
        String month = currentMonth.getDisplayName(TextStyle.FULL, Locale.SIMPLIFIED_CHINESE);
        try {
            emotion=emotionService.selectMonth(name,month);
        }catch (Exception e){
            String message=e.getMessage();
            System.out.println(message);
            message="当前月份："+month+" \n"+message;
            return new Result("500",message,null);
        }

        return Result.success(emotion);
    }

    @AuthAccess
    @GetMapping("/selectAllMonth")
    public Result selectMonth(String name) {
        List<Emotion> emotions = new ArrayList<>();
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 获取当前月份
        Month currentMonth = today.getMonth();
        int monthNumber = today.getMonthValue();
        for(int i=1;i<=monthNumber;i++){
            Emotion emotion;
            String m = String.valueOf(i);
            try {
                emotion=emotionService.selectMonth(name,m);
                if(emotion==null){
                    return Result.success(emotions);
                }
                emotions.add(emotion);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return Result.success(emotions);
    }

    @AuthAccess
    @GetMapping("/selectByMonth")
    public Result selectByMonth(String name,String month) {
        Emotion emotion;
        try {
            emotion = emotionService.selectMonth(name,month);
        }catch (Exception e){
            String message=e.getMessage();
            System.out.println(message);
            return Result.error(message);
        }
        return Result.success(emotion);
    }

    @AuthAccess
    @GetMapping("/selectCharaEmotion")
    public Result selectCharaEmotion(String name) {
        List<Emotion> nowYear = new ArrayList<>();
        List<Emotion> lastYear = new ArrayList<>();
        List<List<String>> emotions = new ArrayList<>();

        LocalDate today = LocalDate.now();
        // 获取当前月份
        int currentYear = today.getYear();
        try {
            nowYear = emotionService.selectByYear(name,currentYear);
            lastYear = emotionService.selectByYear(name,currentYear-1);
            // 使用 Collections.sort() 排序
            Collections.sort(nowYear,new Comparator<Emotion>() {
                @Override
                public int compare(Emotion p1, Emotion p2) {
                    return Integer.compare(Integer.parseInt(p1.getMonth()), Integer.parseInt(p2.getMonth()));
                }
            });
            Collections.sort(lastYear,new Comparator<Emotion>() {
                @Override
                public int compare(Emotion p1, Emotion p2) {
                    return Integer.compare(Integer.parseInt(p1.getMonth()), Integer.parseInt(p2.getMonth()));
                }
            });
            List<String> temp1 = new ArrayList<>();
            List<String> temp2 = new ArrayList<>();
            for (int i = 0; i < nowYear.size(); i++) {
                temp1.add(nowYear.get(i).getGeneralEmotion());
            }
            for (int i = 0; i < lastYear.size(); i++) {
                temp2.add(lastYear.get(i).getGeneralEmotion());
            }
            emotions.add(temp1);
            emotions.add(temp2);
        }catch (Exception e){
            String message=e.getMessage();
            System.out.println(message);
            return Result.error(message);
        }
        return Result.success(emotions);
    }
}
