package com.example.studyspringboot.controller;

import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.service.QianFanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/qianfan")
public class QianFanController {

    private final QianFanService qianFanService;

    /**
     * 处理请求
     * @param content 请求推文内容
     * @return 处理结果
     */
    @GetMapping("/process")
    public Result process(String content) throws IOException {
        return Result.success("200", "success", qianFanService.process(content));
    }
}
