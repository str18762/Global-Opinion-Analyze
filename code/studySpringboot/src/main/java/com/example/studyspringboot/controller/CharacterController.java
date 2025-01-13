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
    /**
     * 选择所有角色
     *
     * 根据用户名获取该用户创建的所有角色信息
     *
     * @param username 用户名，用于查询角色信息的关键字
     * @return 返回包含角色信息列表的Result对象
     */
    @AuthAccess
    @GetMapping("/selectAllChara")
    private Result selectAllChara(String username) {
        List<Character> characters = characterService.selectAllChara(username);
        return Result.success(characters);
    }


    /**
     * 处理角色收藏请求的控制器方法
     * 该方法允许用户收藏特定的角色，通过角色名称和用户名来标识角色和用户
     *
     * @param name 角色名称，用于标识需要被收藏的角色
     * @param username 用户名，用于标识执行收藏操作的用户
     * @return 返回收藏操作的结果，包括成功或失败的消息
     */
    @AuthAccess
    @GetMapping("/collectChara")
    private Result collectChara(String name,String username) {
        // 调用服务层方法，尝试将指定角色添加到用户的收藏列表中
        int row = characterService.addCollect(name,username);

        // 根据添加收藏的操作结果，返回相应的结果消息
        if(row > 0){
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

    @AuthAccess
    @GetMapping("/selectByUsername/{username}")
    private Result selectByUsername(@PathVariable("username") String username) {
        Character character=characterService.selectCharaByUsername(username);
        return Result.success(character);
    }
}
