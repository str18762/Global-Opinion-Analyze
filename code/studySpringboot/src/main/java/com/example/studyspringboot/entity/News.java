package com.example.studyspringboot.entity;

import lombok.Data;

@Data
public class News {
    private int id;
    private String url;
    private String name;
    private String video;
    private String load_img;
    private String title;
    private String title_zh;
    private String content;
    private String content_zh;
    private String source;

}
