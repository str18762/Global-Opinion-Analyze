/**
 * 功能：
 * 作者：
 * 日期：2024/5/1 下午9:34
 */
package com.example.studyspringboot.controller;

import com.example.studyspringboot.common.AuthAccess;
import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.User;
import com.example.studyspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        try {
            userService.insertUser(user);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return Result.error("插入数据库错误");
            } else {
                return Result.error("系统错误");
            }
        }
        return Result.success("插入成功", user.getUsername());
    }

    /**
     * @param id,通过id删除数据
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return Result.error("删除数据错误");
            } else {
                return Result.error("系统错误");
            }
        }
        return Result.success("删除成功", id);
    }



    @AuthAccess
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<User> users = userService.selectAll();
        return Result.success("查询成功", users);
    }

    /**
     * 通过用户ID或用户名来查找唯一用户（ID和用户名都是该用户的唯一标识）
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        User date = userService.selectById(id);
        return Result.success("查询成功", date);
    }

    @GetMapping("/selectByUsername/{username}")
    public Result selectByUsername(@PathVariable String username) {
        User date= userService.selectByUsername(username);
        return Result.success("查询成功", date);
    }

    @AuthAccess
    @PostMapping("/login")
    public Result login(@RequestBody User user)
    {
        if(user.getUsername()==null || user.getPassword()==null){
            return Result.error("输入数据不合法");
        }
        user = userService.login(user);
        return Result.success(user);
    }

    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user)
    {
        if(user.getUsername()==null || user.getPassword()==null){
            return Result.error("输入数据不合法");
        }
        if(user.getUsername().length()>15 || user.getPassword().length()>20){
            return Result.error("用户名或密码过长");
        }
        LocalDate today = LocalDate.now();
        // 格式化日期为 YYYY-MM-DD 格式
        String register_time = today.toString(); // 或者使用 DateTimeFormatter 进行自定义格式化
        user.setRegister_time(register_time);
        user.setFocus_num(0);
        user=userService.register(user);
        return Result.success(user);
    }

    @AuthAccess
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        System.out.println(user);
        try {
           User t= userService.updateUser(user);
            return Result.success("200", "修改成功", t);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (e instanceof DuplicateKeyException) {
                return Result.error("修改失败");
            } else {
                return Result.error("系统错误");
            }
        }
    }

    @AuthAccess
    @GetMapping("/getCollectNum")
    public Result getCollectNum(int id) {
        int i=userService.getCollectNum(id);
        return Result.success(i);
    }
}
