/**
 * 功能：
 * 作者：
 * 日期：2024/10/7 下午12:28
 */
package com.example.studyspringboot.entity;

import lombok.Data;

@Data
public class News_bbc {
    int id;
    String url;
    String article;
    String img;
    String caption;
    String source;
}
