/**
 * 功能：
 * 作者：
 * 日期：2024/5/1 下午9:27
 */
package com.example.studyspringboot.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_tb")
public class User {
    @Id     //指明主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String name = "no_name";
    private String role = "用户";
    private String avatar ="http://localhost:7001/file/download/users/unlogin.jpg";
    private String token;
    //新增字段
    private String description ="展示一下自己吧...";
    private String register_time;
    private int focus_num = 0;
}















