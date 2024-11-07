/**
 * 功能：
 * 作者：
 * 日期：2024/8/24 上午12:11
 */
package com.example.studyspringboot.service;

import com.example.studyspringboot.entity.Emotion;
import com.example.studyspringboot.mapper.EmotionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmotionService {
    @Autowired
    EmotionMapper emotionMapper;

    public Emotion selectMonth(String name, String month) {
        return emotionMapper.selectMonth(name,month);
    }

    public List<Emotion> selectByYear(String name, int year) {
        return emotionMapper.selectByYear(name,String.valueOf(year));
    }
}
