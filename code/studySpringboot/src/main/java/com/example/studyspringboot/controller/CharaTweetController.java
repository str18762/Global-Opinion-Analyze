/**
 * 功能：
 * 作者：
 * 日期：2024/7/22 下午11:49
 */
package com.example.studyspringboot.controller;

import com.example.studyspringboot.common.AuthAccess;
import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.CharaTweet;
import com.example.studyspringboot.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class CharaTweetController {
    @Autowired
    private CharacterService characterService;

    @AuthAccess
    @GetMapping("/president")
    public Result getPresidentTweets(){
        List<CharaTweet> charaTweets = characterService.selectPresidentTweets();
        return Result.success(charaTweets);
    }

    @AuthAccess
    @GetMapping("/politician")
    public Result getPoliticianTweets(){
        List<CharaTweet> charaTweets = characterService.selectPoliticianTweets();
        return Result.success(charaTweets);
    }

    @AuthAccess
    @GetMapping("/diplomats")
    public Result getDiplomatTweets(){
        List<CharaTweet> charaTweets = characterService.selectDiplomatTweets();
        return Result.success(charaTweets);
    }

    @AuthAccess
    @GetMapping("/economist")
    public Result getEconomistTweets(){
        List<CharaTweet> charaTweets = characterService.selectEconomistTweets();
        return Result.success(charaTweets);
    }
}
