package com.example.studyspringboot.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "course")
public class Course {
    @Id     //指明主键
    private int cno;
    private String cname;
    private int cpno;
    private int credit;
}















