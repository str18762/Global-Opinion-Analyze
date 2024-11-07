/**
 * 功能：
 * 作者：
 * 日期：2024/7/22 下午11:46
 */
package com.example.studyspringboot.entity;

import lombok.Data;

@Data
public class CharaTweet {
    private int id;
    private String content;
    private String timestamp;
    private String username;
}
