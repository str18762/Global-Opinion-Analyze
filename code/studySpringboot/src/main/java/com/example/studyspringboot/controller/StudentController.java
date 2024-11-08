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

    /**
     * 插入学生信息
     * @param student 要插入的学生对象
     * @return 插入结果
     */
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
     * 删除学生信息
     * @param sno 学生编号
     * @return 删除结果
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
     * 修改学生信息，目前只允许修改密码
     * @param student 包含新密码的学生对象
     * @return 修改结果
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

    /**
     * 查询所有学生信息
     * @return 学生信息列表
     */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Student> students = studentService.selectAll();
        return Result.success("查询成功", students);
    }

    /**
     * 通过学生编号查询学生信息
     * @param sno 学生编号
     * @return 学生信息
     */
    @GetMapping("/selectBySno/{sno}")
    public Result selectBySno(@PathVariable String sno) {
        Student theSno = studentService.selectBySno(sno);
        return Result.success("查询成功", theSno);
    }

    /**
     * 通过学生姓名查询学生信息
     * @param sname 学生姓名
     * @return 学生信息
     */
    @GetMapping("/selectBySname/{sname}")
    public Result selectBySname(@PathVariable String sname) {
        Student theSname = studentService.selectBySname(sname);
        return Result.success("查询成功", theSname);
    }
}
