/**
 * 功能：
 * 作者：
 * 日期：2024/10/4 下午4:22
 */
package com.example.studyspringboot.controller;

import com.example.studyspringboot.common.AuthAccess;
import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.Character;
import com.example.studyspringboot.entity.Merged;
import com.example.studyspringboot.service.CharacterService;
import com.example.studyspringboot.service.MergedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/merged2")
public class MergedController2 {

    @Autowired
    private MergedService mergedService;
    @Autowired
    private CharacterService characterService;

}
