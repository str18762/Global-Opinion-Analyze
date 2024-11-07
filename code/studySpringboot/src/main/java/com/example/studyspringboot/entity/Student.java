package com.example.studyspringboot.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id     //指明主键
    private String sno;
    private String sname;
    private String ssex;
    private int sage;
    private String sdept;
}















