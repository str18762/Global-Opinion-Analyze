package com.example.studyspringboot.controller;

import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.Merged;
import com.example.studyspringboot.service.MergedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merged")
public class MergedController {

    @Autowired
    private MergedService mergedService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        try {
            List<Merged> users = mergedService.selectAll();
            return Result.success(users);
        } catch (Exception e) {
            return Result.error("500", "请求失败: " + e.getMessage());
        }
    }

    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer id) {
        try {
            Merged user = mergedService.selectById(id);
            if (user != null) {
                return Result.success(user);
            } else {
                return Result.error("404", "用户未找到");
            }
        } catch (Exception e) {
            return Result.error("500", "请求失败: " + e.getMessage());
        }
    }

    @GetMapping("/selectByName")
    public Result selectByName(@RequestParam String name) {
        try {
            List<Merged> users = mergedService.selectByName(name);
            if (!users.isEmpty()) {
                return Result.success(users);
            } else {
                return Result.error("404", "未找到匹配的用户");
            }
        } catch (Exception e) {
            return Result.error("500", "请求失败: " + e.getMessage());
        }
    }
}
