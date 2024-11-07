package com.example.studyspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmotionVO {
    private String name;
    private String year;
    private String month;
    private String detailEmotion;
}
