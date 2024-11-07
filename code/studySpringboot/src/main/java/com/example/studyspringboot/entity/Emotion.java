/**
 * 功能：
 * 作者：
 * 日期：2024/8/23 下午11:57
 */
package com.example.studyspringboot.entity;

import lombok.Data;

@Data
public class Emotion {
    private String name;
    private String year;
    private String month;
    private String detailEmotion;
    private String generalEmotion;
}
