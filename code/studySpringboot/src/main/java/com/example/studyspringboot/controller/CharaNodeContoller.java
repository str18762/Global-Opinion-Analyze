/**
 * 功能：
 * 作者：
 * 日期：2024/11/5 上午9:08
 */
package com.example.studyspringboot.controller;

import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.CharaNode;
import com.example.studyspringboot.service.CharaNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/charaNode")
public class CharaNodeContoller {
    @Autowired
    private CharaNodeService charaNodeService;

    @GetMapping("/getAll")
    public Result getAll() {
        List<CharaNode> charaNodes = charaNodeService.getAll();
        return Result.success(charaNodes);
    }

    @GetMapping("/selectByName")
    public Result selectByName(String name) {
        CharaNode charaNode = charaNodeService.selectByName(name);
        return Result.success(charaNode);
    }
}
