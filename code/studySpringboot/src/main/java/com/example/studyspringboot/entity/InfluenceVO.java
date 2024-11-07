package com.example.studyspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfluenceVO implements Serializable {
    private String mediaExposure;
    private String socialMediaInfluence;
    private String publicOpinion;
    private Integer totalInfluence;
}
