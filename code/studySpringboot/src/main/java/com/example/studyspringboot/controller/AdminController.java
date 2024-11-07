package com.example.studyspringboot.controller;

import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.*;
import com.example.studyspringboot.entity.Character;
import com.example.studyspringboot.service.AdminService;
import com.example.studyspringboot.service.UserService;
import com.example.studyspringboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @GetMapping("/listUser")
    public Result listAllUser(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String userName){
        PageBean<User> users =  adminService.listAllUser(pageNum,pageSize,userName);
        return Result.success(users);
    }

    @DeleteMapping("/deleteUser")
    public Result delete(@RequestParam Integer id){
        if(!checkPrivilege(id)){
            return Result.error("您的权限不够");
        }
        adminService.delete(id);
        return Result.success();
    }

    @DeleteMapping("/batchDeleteUser/{ids}")
    public Result batchDelete(@PathVariable List<Integer> ids) {
        for (Integer id : ids) {
            if(!checkPrivilege(id)){
                return Result.error("您的权限不够");
            }
        }
        adminService.batchDelete(ids);
        return Result.success();
    }

    @PatchMapping("/resetPassword")
    public Result resetPassword(@RequestParam Integer id){
        if(!checkPrivilege(id)){
            return Result.error("您的权限不够");
        }
        adminService.resetPassword(id);
        return Result.success();
    }

    public boolean checkPrivilege(Integer id){
//        User deleteUser = userService.findByUserId(id);
//        Map<String,Object> map = ThreadLocalUtil.get();
//        Integer currentUserId = (Integer) map.get("id");
        User currentUser = TokenUtils.getCurrentUser();
        return true;//currentUser.getRole()!=null && currentUser.getRole().equals("管理员");
    }

    @GetMapping("/listAdmin")
    public Result listAllAdmin(
            Integer pageNum,
            Integer pageSize){
        PageBean<User> users =  adminService.listAllAdmin(pageNum,pageSize);
        return Result.success(users);
    }

    @DeleteMapping("/deleteInfluence")
    public Result deleteInfluence(@RequestParam Integer id){
        if(!checkPrivilege(id)){
            return Result.error("您的权限不够");
        }
        adminService.deleteInfluence(id);
        return Result.success();
    }

    @DeleteMapping("/batchDeleteInfluence/{ids}")
    public Result batchDeleteInfluence(@PathVariable List<Integer> ids) {
        for (Integer id : ids) {
            if(!checkPrivilege(id)){
                return Result.error("您的权限不够");
            }
        }
        adminService.batchDeleteInfluence(ids);
        return Result.success();
    }

    @GetMapping("/listInfluence")
    public Result listAllInfluencePerson(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String userName){
        PageBean<Character> characters =  adminService.listAllInfluencePerson(pageNum,pageSize,userName);
        return Result.success(characters);
    }

    @PostMapping("/updateInfluence")
    public Result updateInfluencePerson(Character character){
        adminService.updateInfluencePerson(character);
        return Result.success();
    }

    @GetMapping("/listNew")
    public Result listAllNews(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String newWhat){
        PageBean<News_old> news =  adminService.listAllNews(pageNum,pageSize,newWhat);
        return Result.success(news);
    }

    @DeleteMapping("/deleteNew")
    public Result deleteNew(@RequestParam String where){
        if(!checkPrivilege(1)){
            return Result.error("您的权限不够");
        }
        adminService.deleteNew(where);
        return Result.success();
    }

    @DeleteMapping("/batchDeleteNew/{wheres}")
    public Result batchDeleteNew(@PathVariable List<String> wheres) {
        if(!checkPrivilege(1)){
            return Result.error("您的权限不够");
        }
        adminService.batchDeleteNew(wheres);
        return Result.success();
    }

    @GetMapping("/listEmotion")
    public Result listAllEmotion(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String detail,
            @RequestParam(required = false) String name){
        PageBean<EmotionVO> emotions =  adminService.listAllEmotion(pageNum,pageSize,detail,name);
        return Result.success(emotions);
    }

    @DeleteMapping("/deleteEmotion")
    public Result deleteEmotion(EmotionVO emotionVO){
        if(!checkPrivilege(1)){
            return Result.error("您的权限不够");
        }
        adminService.deleteEmotion(emotionVO);
        return Result.success();
    }

    @PostMapping("/batchDeleteEmotion")
    public Result batchDeleteEmotion(@RequestBody List<EmotionVO> emotionVOS) {
        if(!checkPrivilege(1)){
            return Result.error("您的权限不够");
        }
        adminService.batchDeleteEmotion(emotionVOS);
        return Result.success();
    }

    @GetMapping("/listExtraEmotion")
    public Result listAllExtraEmotion(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String general,
            @RequestParam(required = false) String name){
        PageBean<EmotionExtraVO> emotions =  adminService.listAllExtraEmotion(pageNum,pageSize,general,name);
        return Result.success(emotions);
    }

    @DeleteMapping("/deleteExtraEmotion")
    public Result deleteExtraEmotion(EmotionExtraVO emotionExtraVO){
        if(!checkPrivilege(1)){
            return Result.error("您的权限不够");
        }
        adminService.deleteExtraEmotion(emotionExtraVO);
        return Result.success();
    }

    @PostMapping("/batchDeleteExtraEmotion")
    public Result batchDeleteExtraEmotion(@RequestBody List<EmotionExtraVO> emotionExtraVOS) {
        if(!checkPrivilege(1)){
            return Result.error("您的权限不够");
        }
        adminService.batchDeleteExtraEmotion(emotionExtraVOS);
        return Result.success();
    }

}
