package com.example.studyspringboot.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "merged")
public class Merged {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String photo;
    private String username;
    private String name;
    private Integer follow;
    private String follow_name;
    private Integer followers;
    private String follower_name;
    private String introduction;
    private String location;
    private String pic;
    private String website;
}
