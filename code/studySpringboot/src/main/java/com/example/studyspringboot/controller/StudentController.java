/**
 * 功能：
 * 作者：
 * 日期：2024/5/1 下午9:34
 */
package com.example.studyspringboot.controller;

import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.Student;
import com.example.studyspringboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public Result add(@RequestBody Student student) {
        try {
            studentService.insertStudent(student);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return Result.error("插入数据库错误");
            } else {
                return Result.error("系统错误");
            }
        }
        return Result.success("插入成功", student.getSno());
    }

    /**
     * @param
     */
    @DeleteMapping("/delete/{sno}")
    public Result delete(@PathVariable String sno) {
        try {
            studentService.deleteStudent(sno);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return Result.error("删除数据错误");
            } else {
                return Result.error("系统错误");
            }
        }
        return Result.success("删除成功", sno);
    }

    /**
     * 修改数据，只允许根据用户ID修改密码（目前）
     */
    @PutMapping("/update")
    public Result update(@RequestBody Student student) {
        try {
            studentService.updateStudent(student);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return Result.error("修改失败");
            } else {
                return Result.error("系统错误");
            }
        }
        return Result.success("200", "修改成功", student.getSno());
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Student> students = studentService.selectAll();
        return Result.success("查询成功", students);
    }

    /**
     * 通过用户ID或用户名来查找唯一用户（ID和用户名都是该用户的唯一标识）
     */
    @GetMapping("/selectBySno/{sno}")
    public Result selectBySno(@PathVariable String sno) {
        Student theSno = studentService.selectBySno(sno);
        return Result.success("查询成功", theSno);
    }

    @GetMapping("/selectBySname/{sname}")
    public Result selectBySname(@PathVariable String sname) {
        Student theSname = studentService.selectBySname(sname);
        return Result.success("查询成功", theSname);
    }
}
