/**
 * 功能：
 * 作者：
 * 日期：2024/7/18 下午9:17
 */
package com.example.studyspringboot.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "celebrity")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String photo;//人物切换为高影响力，photo弃用，使用avatar当做人物头像
    private String avatar;//新增人物头像
    private String background_img;//新增人物背景
    //新增中文_zh
    private String username;
    private String username_zh;
    private String name;
    private String name_zh;
    private String introduction;
    private String introduction_zh;
    private String location;
    private String location_zh;
    private String follow;
    private String followers;
    private String pic;//新版本删除
    private String website;//新版本删除
    private String field;  // 新增字段
    private Boolean collect;//新增字段
}

