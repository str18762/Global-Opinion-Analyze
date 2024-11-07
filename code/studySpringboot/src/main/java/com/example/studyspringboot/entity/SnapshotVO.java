package com.example.studyspringboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotVO {
    private String url;
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private LocalDate date;
}
