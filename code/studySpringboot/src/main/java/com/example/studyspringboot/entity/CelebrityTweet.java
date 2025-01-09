package com.example.studyspringboot.entity;

import lombok.Data;

/**
 * 高影响力人物推文
 */
@Data
public class CelebrityTweet {
    // 主键
    private int id;

    // 高影响力人物
    private String author;

    // 推文时间
    private String time;

    // 推文内容
    private String content;

    // 描述信息
    private String description;
}
