/**
 * 功能：
 * 作者：
 * 日期：2024/7/18 下午9:16
 */
package com.example.studyspringboot.controller;


import com.example.studyspringboot.common.AuthAccess;
import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.Character;
import com.example.studyspringboot.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    //1.5版本更新后勿用，使用下面新增的selectAllChara，可以带是否关注属性返回，可以不向接口传username
    @AuthAccess
    @GetMapping("/selectAll")
    private Result selectAll() {
        List<Character> characters=characterService.selectAll();
        return Result.success(characters);
    }

    @AuthAccess
    @GetMapping("/influence")
    private Result getPersonInfluence(String name) {
        return Result.success(characterService.getPersonInfluence(name));
    }

    @AuthAccess
    @GetMapping("/getFans")
    private Result getFansNum() {
        return Result.success(characterService.getFansNum());
    }

    @AuthAccess
    @GetMapping("/getComment")
    private Result getCommentNum() {
        return Result.success(characterService.getCommentNum());
    }

    @AuthAccess
    @GetMapping("/selectByField")
    private Result selectByField(@RequestParam String field) {
        List<Character> characters = characterService.selectByField(field);
        return Result.success(characters);
    }

    //1.5新加在下
    @AuthAccess
    @GetMapping("/selectAllChara")
    private Result selectAllChara(String username) {
        List<Character> characters = characterService.selectAllChara(username);
        return Result.success(characters);
    }

    @AuthAccess
    @GetMapping("/collectChara")
    private Result collectChara(String name,String username) {
        int row = characterService.addCollect(name,username);
        if(row>0){
            return Result.success("收藏成功");
        }
        else {
            return Result.error("收藏失败");
        }
    }

    @AuthAccess
    @GetMapping("/cancelCollect")
    private Result cancelCollect(String name,String username) {
        int row = characterService.deleteCollect(name,username);
        if(row>0){
            return Result.success("取消收藏成功");
        }
        else {
            return Result.error("取消收藏失败");
        }
    }

    //1.6改动，可不传username
    @AuthAccess
    @GetMapping("/select/{name}")
    private Result selectByName(@PathVariable("name") String name,String username) {
        Character character=characterService.selectCharaByName(name,username);
        return Result.success(character);
    }

    @AuthAccess
    @GetMapping("/selectCollectChara")
    private Result selectCollectChara(String username) {
        List<Character> characters = characterService.selectCollectChara(username);
        return Result.success(characters);
    }

    //1.7在tochinaemotion表中根据情感查人物
    @AuthAccess
    @GetMapping("/selectCharaByEmotion")
    private Result selectCharaByEmotion(String emotion) {
        List<Character> characters = characterService.selectCharaByEmotion(emotion);
        return Result.success(characters);
    }
}
