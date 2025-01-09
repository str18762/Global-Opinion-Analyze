package com.example.studyspringboot.service;

import com.example.studyspringboot.api.QianFanAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 百度智能云问答机器人服务
 */
@Service
@RequiredArgsConstructor
public class QianFanService {

    private final QianFanAPI qianFanAPI;

    public String process(String content) throws IOException {
        return qianFanAPI.process(content);
    }

    public List<String> relation(String content) throws IOException {
        return qianFanAPI.relation(content);
    }
}
